package com.teamawesome.navii.fragment.intro;

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
//        BootstrapCircleThumbnail mPreference1 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_1);
//        BootstrapCircleThumbnail mPreference2 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_2);
//        BootstrapCircleThumbnail mPreference3 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_3);
//        BootstrapCircleThumbnail mPreference4 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_4);
//        BootstrapCircleThumbnail mPreference5 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_5);
//        BootstrapCircleThumbnail mPreference6 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_6);
//        BootstrapCircleThumbnail mPreference7 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_7);
//        BootstrapCircleThumbnail mPreference8 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_8);
//        BootstrapCircleThumbnail mPreference9 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_9);
//        BootstrapCircleThumbnail mPreference10 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_10);
//        BootstrapCircleThumbnail mPreference11 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_11);
//        BootstrapCircleThumbnail mPreference12 = (BootstrapCircleThumbnail) view.findViewById(R.id.preferences_12);

//        mPreference1.setOnClickListener(mOnClickListener);
//        mPreference2.setOnClickListener(mOnClickListener);
//        mPreference3.setOnClickListener(mOnClickListener);
//        mPreference4.setOnClickListener(mOnClickListener);
//        mPreference5.setOnClickListener(mOnClickListener);
//        mPreference6.setOnClickListener(mOnClickListener);
//        mPreference7.setOnClickListener(mOnClickListener);
//        mPreference8.setOnClickListener(mOnClickListener);
//        mPreference9.setOnClickListener(mOnClickListener);
//        mPreference10.setOnClickListener(mOnClickListener);
//        mPreference11.setOnClickListener(mOnClickListener);
//        mPreference12.setOnClickListener(mOnClickListener);
        return view;

    }

    View.OnClickListener mButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("PreferenceFragment", "mButtonOnClickListener()");
            Log.d("PreferenceFragment", "List: " + mSelectedPrefereces.toString());

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

            // This does an async call.
            // Use "execute" instead for a sync call.
            // Call "call.cancel()" to cancel a running request.
            call.enqueue(new Callback<UserPreference>() {
                @Override
                public void onResponse(Response<UserPreference> response, Retrofit retrofit) {
                    Log.i("response: code", String.valueOf(response.code()));
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.i("failed", t.getMessage());
                }
            });

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
        Log.d("PreferenceFragment", "onClick()" + view.getTag());

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