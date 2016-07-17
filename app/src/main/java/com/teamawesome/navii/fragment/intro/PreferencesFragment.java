package com.teamawesome.navii.fragment.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamawesome.navii.NaviiApplication;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.IntroActivity;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.adapter.PreferencesGridAdapter;
import com.teamawesome.navii.fragment.main.ChooseTagsFragment;
import com.teamawesome.navii.fragment.main.NaviiFragment;
import com.teamawesome.navii.server.model.Preference;
import com.teamawesome.navii.server.model.PreferencesQuestion;
import com.teamawesome.navii.server.model.UserPreference;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.NaviiPreferenceData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JMtorii on 2015-10-30.
 */
public class PreferencesFragment extends NaviiFragment {
    private static final String PREFERENCE_TYPE = "preference_type";

    @BindView (R.id.preferences_next_button)
    Button mNextButton;

    @BindView(R.id.preferences_layout)
    RecyclerView gridView;

    @BindView(R.id.preferences_text)
    TextView textView;

    private PreferencesGridAdapter mAdapter;
    private NaviiApplication mApplication = NaviiApplication.getInstance();

    public static PreferencesFragment newInstance(int preferenceType) {
        PreferencesFragment fragment = new PreferencesFragment();
        Bundle arg = new Bundle();
        arg.putInt(PREFERENCE_TYPE, preferenceType);
        fragment.setArguments(arg);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_preferences, container, false);
        ButterKnife.bind(this, view);
        final int preferenceType = getArguments().getInt(PREFERENCE_TYPE);
        //TODO: change to server implementation
        final int numberOfPreferences = 3;

        Observable<PreferencesQuestion> observable =
            NaviiApplication.getInstance().getPreferenceAPI().getPreferences(preferenceType);

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PreferencesQuestion>() {
                    @Override
                    public void onCompleted() {
                        //nothing to do here
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("PreferencesFragment", "onError", e);
                    }

                    @Override
                    public void onNext(PreferencesQuestion preferenceQuestion) {
                        textView.setText(preferenceQuestion.getQuestion());
                        mAdapter = new PreferencesGridAdapter(preferenceQuestion.getPreferences());
                        gridView.setAdapter(mAdapter);
                        gridView.setLayoutManager(new GridLayoutManager(gridView.getContext(), numberOfPreferences));
                    }
                });


        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter.getItemCount() < Constants.PREFERENCE_MIN_LIMIT) {
                    //TODO: Replace with toast replacement
                    Toast.makeText(getContext(), "Less than minimum requirements", Toast.LENGTH_LONG).show();
                    return;
                }

                String username = NaviiPreferenceData.getLoggedInUserEmail();
                // TODO : Change id to the one in shared preferences
                UserPreference userPreference = new UserPreference.Builder()
                        .username(username)
                        .preferences(mAdapter.getmSelectedPreferences())
                        .build();

                // TODO : Change id to the one in shared preferences
                Log.d("PreferenceFragment: ", username);

                Call<Void> deleteCall = mApplication.getUserPreferenceAPI().deleteAllUserPreference(username, preferenceType);

                Call<Void> createCall = mApplication.getUserPreferenceAPI().createUserPreference(userPreference);

                // enqueues the delete call to delete the existing preferences for the user to
                // replace with new ones
                deleteCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Response<Void> response, Retrofit retrofit) {
                        Log.i("response: code", String.valueOf(response.code()));
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.i("failed", t.getMessage());
                    }
                });

                // enqueues the create call to create the selected preferences for the user
                createCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Response<Void> response, Retrofit retrofit) {
                        Log.i("call response: code", String.valueOf(response.code()));
                        //Switch to the next preference type  (change screens)
                        if (response.code() == 201) {
                            int nextPreference = preferenceType + 1;
                            if (nextPreference <= numberOfPreferences) {
                                getFragmentManager().beginTransaction().replace(R.id.preference_fragment_frame,  PreferencesFragment.newInstance(nextPreference)).commit();
                            } else {
                                // If the activity is in the intro stage
                                if (getActivity().getClass().equals(IntroActivity.class)) {
                                    Intent intent = new Intent(parentActivity, MainActivity.class);
                                    parentActivity.startActivity(intent);
                                } else if (getActivity().getClass().equals(MainActivity.class)){
                                    parentActivity.switchFragment(
                                            new ChooseTagsFragment(),
                                            Constants.NO_ANIM,
                                            Constants.NO_ANIM,
                                            Constants.PLANNING_CHOOSE_TAGS_FRAGMENT_TAG,
                                            true,
                                            true,
                                            true);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.i("failed", t.getMessage());
                    }
                });
            }
        });

        return view;
    }
}