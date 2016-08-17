package com.teamawesome.navii.fragment.intro;

import android.content.Context;
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
import com.teamawesome.navii.activity.PreferencesActivity;
import com.teamawesome.navii.adapter.PreferencesGridAdapter;
import com.teamawesome.navii.server.model.Preference;
import com.teamawesome.navii.server.model.PreferencesQuestion;
import com.teamawesome.navii.util.RestClient;

import java.util.List;

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
    private PreferencesActivity preferencesActivity;
    public static PreferencesFragment newInstance(int preferenceType) {
        PreferencesFragment fragment = new PreferencesFragment();
        Bundle arg = new Bundle();
        arg.putInt(PREFERENCE_TYPE, preferenceType);
        fragment.setArguments(arg);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        preferencesActivity = (PreferencesActivity) this.getActivity();

        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_preferences, container, false);
        ButterKnife.bind(this, view);
        final int preferenceType = getArguments().getInt(PREFERENCE_TYPE);
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
                        mAdapter = new PreferencesGridAdapter(preferenceQuestion.getPreferences(), preferencesActivity.getPrefetchedPreferences(preferenceType-1));
                        gridView.setAdapter(mAdapter);
                        gridView.setLayoutManager(new GridLayoutManager(gridView.getContext(), numberOfPreferences));
                    }
                });

        return view;
    }
    public List<Preference> getSelectedPreferences() {
        return mAdapter.getSelectedPreferences();
    }
}