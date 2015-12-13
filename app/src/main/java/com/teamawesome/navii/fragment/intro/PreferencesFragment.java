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
import android.widget.Toast;

import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.IntroActivity;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.adapter.PreferencesGridAdapter;
import com.teamawesome.navii.fragment.main.MainFragment;
import com.teamawesome.navii.server.model.Preference;
import com.teamawesome.navii.server.model.UserPreference;
import com.teamawesome.navii.util.Constants;

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
public class PreferencesFragment extends MainFragment {
    private static final String PREFERENCE_TYPE = "preference_type";

    private Button mNextButton;
    private GridView gridView;
    private ImageView imageView;

    private List<Preference> mSelectedPreferences;
    private int mPreferencesCount;

    public static PreferencesFragment newInstance(int preferenceType) {
        PreferencesFragment fragment = new PreferencesFragment();
        Bundle arg = new Bundle();
        arg.putInt(PREFERENCE_TYPE, preferenceType);
        fragment.setArguments(arg);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelectedPreferences = new ArrayList<>();
        mPreferencesCount = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_preferences, container, false);

        mNextButton = (Button) view.findViewById(R.id.preferences_next_button);
        gridView = (GridView) view.findViewById(R.id.preferences_layout);

        int preferenceType = getArguments().getInt(PREFERENCE_TYPE);
        Observable<List<Preference>> observable = parentActivity.preferenceAPI.getPreferences(preferenceType);

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Preference>>() {
                    @Override
                    public void onCompleted() {
                        //nothing to do here
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("PreferencesFragment", "onError", e);
                    }

                    @Override
                    public void onNext(List<Preference> preferences) {
                        gridView.setAdapter(new PreferencesGridAdapter(getContext(), R.layout.preferences_view, preferences));

                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                imageView = (ImageView) view.findViewById(R.id.preferenceCheckImageView);

                                boolean selected = imageView.isSelected();
                                if (selected) {
                                    if (mPreferencesCount == Constants.PREFERENCE_MAX_LIMIT) {
                                        return;
                                    }
                                    mSelectedPreferences.add((Preference) imageView.getTag());
                                    ++mPreferencesCount;
                                    imageView.setVisibility(View.VISIBLE);
                                } else {
                                    mSelectedPreferences.remove((Preference) imageView.getTag());
                                    --mPreferencesCount;
                                    imageView.setVisibility(View.GONE);
                                }
                                imageView.setSelected(!selected);
                            }
                        });
                    }
                });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: don't return early.
                if (mPreferencesCount < Constants.PREFERENCE_MIN_LIMIT) {
                    //TODO: Replace with toast replacement
                    Toast.makeText(getContext(), "Less than minimum requirements", Toast.LENGTH_LONG).show();
                    return;
                }

                UserPreference userPreference = new UserPreference.Builder()
                        .username("android-user")
                        .preferences(mSelectedPreferences)
                        .build();

                Call<UserPreference> deleteCall = parentActivity.userPreferenceAPI.deleteAllUserPreference("android-user");
                Call<UserPreference> createCall = parentActivity.userPreferenceAPI.createUserPreference(userPreference);

                // TODO: wtf does this do? Add comments.
                deleteCall.enqueue(new Callback<UserPreference>() {
                    @Override
                    public void onResponse(Response<UserPreference> response, Retrofit retrofit) {
                        Log.i("response: code", String.valueOf(response.code()));
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.i("failed", t.getMessage());
                        Toast.makeText(getContext(), "Could not update", Toast.LENGTH_LONG).show();
                    }
                });

                createCall.enqueue(new Callback<UserPreference>() {
                    @Override
                    public void onResponse(Response<UserPreference> response, Retrofit retrofit) {
                        Log.i("response: code", String.valueOf(response.code()));
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.i("failed", t.getMessage());
                        Toast.makeText(getContext(), "Could not update", Toast.LENGTH_LONG).show();
                    }
                });

                // If we're a part of the INTRO stage
                if (getActivity().getClass().equals(IntroActivity.class)) {
                    Intent intent = new Intent(parentActivity, MainActivity.class);
                    parentActivity.startActivity(intent);
                }
            }
        });

        return view;
    }
}