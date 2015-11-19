package com.teamawesome.navii.fragment.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.teamawesome.navii.server.api.UserPreferenceAPI;
import com.teamawesome.navii.server.model.UserPreference;
import com.teamawesome.navii.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by JMtorii on 2015-10-30.
 */
public class PreferencesFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Button mNextButton;
    private List<String> mSelectedPrefereces;
    private int mPreferencesCount;

    public PreferencesFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro_preferences, container, false);
        mSelectedPrefereces = new ArrayList<>();
        mPreferencesCount = 0;

        mNextButton = (Button) view.findViewById(R.id.preferences_next_button);
        mNextButton.setOnClickListener(mButtonOnClickListener);

        return view;

    }

    View.OnClickListener mButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (mPreferencesCount < 5) {
                Toast.makeText(getContext(), "You have less than 5 selected preferences", Toast.LENGTH_LONG).show();
                return;
            }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.SERVER_URL)    // THIS ONLY WORKS ON JUN'S CASE
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            UserPreferenceAPI userPreferenceAPI = retrofit.create(UserPreferenceAPI.class);

            UserPreference userPreference = new UserPreference.Builder()
                    .username("android-user")
                    .preferences((List<String>) mSelectedPrefereces)
                    .build();

            Call<UserPreference> call = userPreferenceAPI.createUserPreference(userPreference);

            Call<UserPreference> deletecall = userPreferenceAPI.deleteAllUserPreference("android-user");

            // This does an async call.
            // Use "execute" instead for a sync call.
            // Call "call.cancel()" to cancel a running request.

            call.enqueue(preferenceCallBack);

            if (getActivity().getClass() == IntroActivity.class) {

                Intent intent = new Intent(getActivity(), MainActivity.class);

                getContext().startActivity(intent);
            }

        }
    };

    Callback<UserPreference> preferenceCallBack = new Callback<UserPreference>() {
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
//    View.OnClickListener mOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Log.d("PreferenceFragment", "onClick()");
//
//
//        }
//    };

    public void preferencesOnClick(View view) {
        BootstrapCircleThumbnail bootstrapCircleThumbnail = (BootstrapCircleThumbnail) view;
        boolean isSelected = !bootstrapCircleThumbnail.isSelected();

        if (isSelected) {
            if (mPreferencesCount == 5) {
                Toast.makeText(getContext(), "Cannot add another preference", Toast.LENGTH_LONG).show();
                return;
            }
            mSelectedPrefereces.add((String) bootstrapCircleThumbnail.getTag());
            mPreferencesCount++;
        }
        else {

            mSelectedPrefereces.remove(bootstrapCircleThumbnail.getTag());
            mPreferencesCount--;
        }

        bootstrapCircleThumbnail.setSelected(isSelected);
        bootstrapCircleThumbnail.setBorderDisplayed(isSelected);

    }
}