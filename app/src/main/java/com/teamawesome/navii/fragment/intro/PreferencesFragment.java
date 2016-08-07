package com.teamawesome.navii.fragment.intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.PreferencesGridAdapter;
import com.teamawesome.navii.server.model.PreferencesQuestion;
import com.teamawesome.navii.util.RestClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JMtorii on 2015-10-30.
 */
public class PreferencesFragment extends Fragment {
    private static final String PREFERENCE_TYPE = "preference_type";

    @BindView(R.id.preferences_layout)
    RecyclerView gridView;

    @BindView(R.id.preferences_text)
    TextView textView;

    private PreferencesGridAdapter mAdapter;

    public static PreferencesFragment newInstance(int preferenceType) {
        PreferencesFragment fragment = new PreferencesFragment();
        Bundle arg = new Bundle();
        arg.putInt(PREFERENCE_TYPE, preferenceType);
        fragment.setArguments(arg);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_preferences, container, false);
        ButterKnife.bind(this, view);
        final int preferenceType = getArguments().getInt(PREFERENCE_TYPE);
        //TODO: change to server implementation
        final int numberOfPreferences = 3;

        Observable<PreferencesQuestion> observable = RestClient.preferenceAPI.getPreferences(preferenceType);

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


//        mNextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mAdapter.getItemCount() < Constants.PREFERENCE_MIN_LIMIT) {
//                    //TODO: Replace with toast replacement
//                    Toast.makeText(getContext(), "Less than minimum requirements", Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                String email = NaviiPreferenceData.getLoggedInUserEmail();
//
//                for (Preference preference : mAdapter.getSelectedPreferences()) {
//                    Log.d("Preference", preference.getPreference());
//                }
//
//                Call<Void> deleteCall = RestClient.userPreferenceAPI.deleteAllUserPreference(preferenceType);
//                Call<Void> createCall = RestClient.userPreferenceAPI.createUserPreference(mAdapter.getSelectedPreferences());
//
//                // enqueues the delete call to delete the existing preferences for the user to
//                // replace with new ones
//                deleteCall.enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Response<Void> response, Retrofit retrofit) {
//                        Log.i("Delete: code", String.valueOf(response.code()));
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        Log.i("failed", t.getMessage());
//                    }
//                });
//
//                // enqueues the create call to create the selected preferences for the user
//                createCall.enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Response<Void> response, Retrofit retrofit) {
//                        Log.i("call response: code", String.valueOf(response.code()));
//                        //Switch to the next preference type  (change screens)
//                        if (response.code() == 201) {
//                            int nextPreference = preferenceType + 1;
//                            if (nextPreference <= numberOfPreferences) {
//                            } else {
//                                // If the activity is in the intro stage
//
//                                // TODO: delete if needed
////                                if (getActivity().getClass().equals(IntroActivity.class)) {
////                                    Intent intent = new Intent(parentActivity, MainActivity.class);
////                                    parentActivity.startActivity(intent);
////                                } else if (getActivity().getClass().equals(MainActivity.class)){
////                                    parentActivity.switchFragment(
////                                            new ChooseTagsFragment(),
////                                            Constants.NO_ANIM,
////                                            Constants.NO_ANIM,
////                                            Constants.PLANNING_CHOOSE_TAGS_FRAGMENT_TAG,
////                                            true,
////                                            true,
////                                            true);
////                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        Log.i("failed", t.getMessage());
//                    }
//                });
//            }
//        });

        return view;
    }
}