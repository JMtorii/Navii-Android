package com.teamawesome.navii.fragment.intro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.IntroActivity;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.fragment.main.MainFragment;
import com.teamawesome.navii.server.api.PreferenceAPI;
import com.teamawesome.navii.server.model.Preference;
import com.teamawesome.navii.server.model.UserPreference;
import com.teamawesome.navii.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JMtorii on 2015-10-30.
 */
public class PreferencesFragment extends MainFragment {
    private Button mNextButton;
    private List<String> mSelectedPreferences;
    private int mPreferencesCount;

    private static final String PREFERENCE_TYPE = "preference_type";

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_intro_preferences, container, false);

        mSelectedPreferences = new ArrayList<>();
        mPreferencesCount = 0;

        mNextButton = (Button) view.findViewById(R.id.preferences_next_button);
        mNextButton.setOnClickListener(mButtonOnClickListener);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();


        int preferenceType = getArguments().getInt(PREFERENCE_TYPE);
        final List<Preference> preferencesList = new ArrayList<>();

        Observable<List<Preference>> observable = retrofit.create(PreferenceAPI.class)
                .getPreferences(preferenceType);

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Preference>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("PreferencesFragment", "onError", e);
                    }

                    @Override
                    public void onNext(List<Preference> preferences) {
                        GridView gridView = (GridView) view.findViewById(R.id.preferences_layout);

                        gridView.setAdapter(new PreferencesGridAdapter(getContext(), R.layout
                                .prefrences_view, preferences));

                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                CheckBox checkBox = (CheckBox) view.findViewById(R.id.preferenceCheckBox);
                                if (checkBox.isSelected()) {
                                    if (mPreferencesCount == Constants.PREFERENCE_MAX_LIMIT) {
                                        return;
                                    }
                                    mSelectedPreferences.add((String) checkBox.getTag());
                                    mPreferencesCount++;
                                } else {

                                    mSelectedPreferences.remove(checkBox.getTag());
                                    mPreferencesCount--;
                                }
                            }
                        });

                    }
                });

        return view;
    }

    private View.OnClickListener mButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

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
            Call<UserPreference> call = parentActivity.userPreferenceAPI.createUserPreference(userPreference);

            deleteCall.enqueue(preferenceCallBack);
            call.enqueue(preferenceCallBack);

            if (getActivity().getClass().equals(IntroActivity.class)) {
                Intent intent = new Intent(parentActivity, MainActivity.class);
                parentActivity.startActivity(intent);
            }
        }
    };

    private View.OnClickListener mPreferencesOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


        }
    };
    private Callback<UserPreference> preferenceCallBack = new Callback<UserPreference>() {
        @Override
        public void onResponse(Response<UserPreference> response, Retrofit retrofit) {
            Log.i("response: code", String.valueOf(response.code()));
        }

        @Override
        public void onFailure(Throwable t) {
            Log.i("failed", t.getMessage());
            Toast.makeText(getContext(), "Could not update", Toast.LENGTH_LONG).show();
        }
    };

    private class PreferencesGridAdapter extends ArrayAdapter<Preference> {

        List<Preference> mPreferences;

        public PreferencesGridAdapter(Context context, int resource, List<Preference> preferences) {
            super(context, resource, preferences);
            this.mPreferences = preferences;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.prefrences_view, null);
            }

            ImageView imageView = (ImageView) view.findViewById(R.id.preferenceImageView);
            imageView.setImageResource(R.drawable.imagination);

            CheckBox checkBox = (CheckBox) view.findViewById(R.id.preferenceCheckBox);
            checkBox.setText(mPreferences.get(position).getPreference());

            return view;
        }

    }

}