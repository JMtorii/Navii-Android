package com.teamawesome.navii.fragment.intro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.IntroActivity;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.fragment.main.MainFragment;
import com.teamawesome.navii.server.model.UserPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by JMtorii on 2015-10-30.
 */
public class PreferencesFragment extends MainFragment {
    private Button mNextButton;
    private List<String> mSelectedPreferences;
    private int mPreferencesCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro_preferences, container, false);

        mSelectedPreferences = new ArrayList<>();
        mPreferencesCount = 0;

        mNextButton = (Button) view.findViewById(R.id.preferences_next_button);
        mNextButton.setOnClickListener(mButtonOnClickListener);

        // Circle Thumbnail setup
        BootstrapCircleThumbnail preferences1 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_1);
        BootstrapCircleThumbnail preferences2 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_2);
        BootstrapCircleThumbnail preferences3 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_3);
        BootstrapCircleThumbnail preferences4 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_4);
        BootstrapCircleThumbnail preferences5 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_5);
        BootstrapCircleThumbnail preferences6 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_6);
        BootstrapCircleThumbnail preferences7 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_7);
        BootstrapCircleThumbnail preferences8 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_8);
        BootstrapCircleThumbnail preferences9 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_9);
        BootstrapCircleThumbnail preferences10 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_10);
        BootstrapCircleThumbnail preferences11 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_11);
        BootstrapCircleThumbnail preferences12 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_12);

        preferences1.setOnClickListener(mPreferencesOnClickListener);
        preferences2.setOnClickListener(mPreferencesOnClickListener);
        preferences3.setOnClickListener(mPreferencesOnClickListener);
        preferences4.setOnClickListener(mPreferencesOnClickListener);
        preferences5.setOnClickListener(mPreferencesOnClickListener);
        preferences6.setOnClickListener(mPreferencesOnClickListener);
        preferences7.setOnClickListener(mPreferencesOnClickListener);
        preferences8.setOnClickListener(mPreferencesOnClickListener);
        preferences9.setOnClickListener(mPreferencesOnClickListener);
        preferences10.setOnClickListener(mPreferencesOnClickListener);
        preferences11.setOnClickListener(mPreferencesOnClickListener);
        preferences12.setOnClickListener(mPreferencesOnClickListener);

        return view;
    }

    private View.OnClickListener mButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (mPreferencesCount < 5) {
                Toast.makeText(getContext(), "You have less than 5 selected preferences", Toast.LENGTH_LONG).show();
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
        public void onClick(View v) {

            BootstrapCircleThumbnail bootstrapCircleThumbnail = (BootstrapCircleThumbnail) v;
            boolean isSelected = !bootstrapCircleThumbnail.isSelected();

            if (isSelected) {
                if (mPreferencesCount == 5) {
                    Toast.makeText(getContext(), "Cannot add another preference", Toast.LENGTH_LONG).show();
                    return;
                }
                mSelectedPreferences.add((String) bootstrapCircleThumbnail.getTag());
                mPreferencesCount++;
            } else {

                mSelectedPreferences.remove(bootstrapCircleThumbnail.getTag());
                mPreferencesCount--;
            }

            bootstrapCircleThumbnail.setSelected(isSelected);
            bootstrapCircleThumbnail.setBorderDisplayed(isSelected);
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
            Toast.makeText(getContext(), "Could not update",Toast.LENGTH_LONG).show();
        }
    };

}