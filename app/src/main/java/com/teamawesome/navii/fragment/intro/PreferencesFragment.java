package com.teamawesome.navii.fragment.intro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
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

    private Button mNextButton;
    private GridView gridView;
    private ImageView imageView;
    private TextView textView;
    private List<Preference> mSelectedPreferences;
    private int mPreferencesCount;
    private int numberOfPreferences;

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

        mNextButton = (Button) view.findViewById(R.id.preferences_next_button);
        gridView = (GridView) view.findViewById(R.id.preferences_layout);
        textView = (TextView) view.findViewById(R.id.preferences_text);

        final int preferenceType = getArguments().getInt(PREFERENCE_TYPE);
        mSelectedPreferences = new ArrayList<>();
        mPreferencesCount = 0;
        //TODO: change to server implementation
        numberOfPreferences = 3;

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
                        gridView.setAdapter(
                                new PreferencesGridAdapter(getContext(),
                                R.layout.adapter_preferences,
                                preferenceQuestion.getPreferences())
                        );
                    }
                });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imageView = (ImageView) view.findViewById(R.id.preferenceCheckImageView);
                if (!imageView.isSelected()) {
                    if (mPreferencesCount == Constants.PREFERENCE_MAX_LIMIT) {
                        return;
                    }
                    mSelectedPreferences.add((Preference) view.getTag());
                    ++mPreferencesCount;
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    mSelectedPreferences.remove((Preference) view.getTag());
                    --mPreferencesCount;
                    imageView.setVisibility(View.GONE);
                }
                imageView.setSelected(!imageView.isSelected());
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPreferencesCount < Constants.PREFERENCE_MIN_LIMIT) {
                    //TODO: Replace with toast replacement
                    Toast.makeText(getContext(), "Less than minimum requirements", Toast.LENGTH_LONG).show();
                    return;
                }

                for (Preference preference : mSelectedPreferences) {
                    Log.d("onClick", preference.getPreference());
                }
                String username = NaviiPreferenceData.getLoggedInUserEmail();
                // TODO : Change id to the one in shared preferences
                UserPreference userPreference = new UserPreference.Builder()
                        .username(username)
                        .preferences(mSelectedPreferences)
                        .build();

                // TODO : Change id to the one in shared preferences
                Log.d("PreferenceFragment: ", username);
                Call<Void> deleteCall = parentActivity.userPreferenceAPI.deleteAllUserPreference
                        (username, preferenceType);
                Call<Void> createCall = parentActivity.userPreferenceAPI.createUserPreference(userPreference);

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

                        if (response.code() == 201) {
                            int nextPreference = preferenceType + 1;
                            if (nextPreference <= numberOfPreferences) {
                                parentActivity.switchFragment(
                                        PreferencesFragment.newInstance(nextPreference),
                                        Constants.NO_ANIM,
                                        Constants.NO_ANIM,
                                        Constants.PREFERENCES_FRAGMENT_TAG,
                                        true,
                                        true,
                                        true
                                );
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