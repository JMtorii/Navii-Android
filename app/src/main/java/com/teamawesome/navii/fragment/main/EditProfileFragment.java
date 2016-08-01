package com.teamawesome.navii.fragment.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.squareup.okhttp.ResponseBody;
import com.teamawesome.navii.R;

import com.teamawesome.navii.server.model.User;
import com.teamawesome.navii.util.NaviiPreferenceData;
import com.teamawesome.navii.util.RestClient;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by IK on 25/12/2015.
 */
public class EditProfileFragment extends NaviiFragment {

    private BootstrapEditText newEmail;

    private BootstrapButton commitEmail;

    public static EditProfileFragment newInstance(){
        EditProfileFragment fragment = new EditProfileFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_edit_profile,container,false);
        newEmail = (BootstrapEditText) v.findViewById(R.id.edit_profile_new_email);
        commitEmail = (BootstrapButton) v.findViewById(R.id.edit_profile_commit_email);
        commitEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence email = newEmail.getText();
                if (isValidEmail(email)){
                    User userToUpdate = new User.Builder()
                            .username(NaviiPreferenceData.getLoggedInUserEmail())
                            .isFacebook(NaviiPreferenceData.isFacebook())
                            .build();
                    Call<ResponseBody> updateCall = RestClient.userAPI.updateUser(
                            userToUpdate,
                            email.toString());

                    updateCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                            if (response.code() == 200) {
                                NaviiPreferenceData.setLoggedInUserEmail(email.toString());
                                parentActivity.getSupportFragmentManager().popBackStackImmediate();
                            } else {
                                Log.i("failed", String.valueOf(response.code()));
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.i("failed", t.getMessage());
                        }
                    });
                } else {
                    animateWrongCredentials(R.id.edit_profile_new_email);
                }
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    private void animateWrongCredentials(int id) {
        // TODO: implement me
    }

    private boolean isValidEmail(CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && !email.toString().equalsIgnoreCase(NaviiPreferenceData.getLoggedInUserEmail());
    }

}
