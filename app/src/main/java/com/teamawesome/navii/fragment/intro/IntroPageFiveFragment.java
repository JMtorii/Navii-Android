package com.teamawesome.navii.fragment.intro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.IntroActivity;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.NaviiPreferenceData;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by JMtorii on 15-09-22.
 */
public class IntroPageFiveFragment extends IntroAbstractPageFragment {

    private IntroActivity parentActivity;
    private BootstrapButton mSignUpButton;
    private BootstrapButton mLoginButton;
    private BootstrapEditText mEmailEditText;
    private BootstrapEditText mPassWordEditText;
    private AwesomeTextView mErrorText;

    public static IntroPageFiveFragment newInstance(int position) {
        IntroPageFiveFragment f = new IntroPageFiveFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (IntroActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_intro_page5, container, false);
        mSignUpButton = (BootstrapButton) v.findViewById(R.id.intro_page5_sign_up_button);
        mLoginButton = (BootstrapButton) v.findViewById(R.id.intro_page5_login_button);
        mEmailEditText = (BootstrapEditText) v.findViewById(R.id.intro_page5_email_edittext);
        mPassWordEditText = (BootstrapEditText) v.findViewById(R.id.intro_page5_password_edittext);
        mErrorText = (AwesomeTextView) v.findViewById(R.id.intro_page5_error);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = parentActivity.userAPI.signUp(mEmailEditText.getText().toString(), mPassWordEditText.getText().toString());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Response<Void> response, Retrofit retrofit) {
                        if (response.code() == 400) {
                            Log.w("Sign Up", "Failed: Username or password is blank");
                            mErrorText.setText("Username and/or password is blank.");
                            animateWrongCredentials();
                        } else if (response.code() == 409) {
                            Log.w("Sign Up", "Failed: User already exists");
                            mErrorText.setText("Username already exists.");
                            animateWrongCredentials();
                        } else if (response.code() == 200) {
                            NaviiPreferenceData.setLoggedInUserEmail(mEmailEditText.getText().toString());

                            IntroThanksFragment fragment = new IntroThanksFragment();
                            parentActivity.switchFragment(
                                    fragment,
                                    Constants.NO_ANIM,
                                    Constants.NO_ANIM,
                                    Constants.INTRO_THANKS_FRAGMENT_TAG,
                                    true,
                                    true,
                                    true
                            );
                        } else {
                            Log.w("Sign Up", "Failed: WTF did we get. Response code: " + String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.w("Sign Up", "Failed: " + t.getMessage());
                        mErrorText.setText("Internal server error.");
                    }
                });
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = parentActivity.userAPI.login(mEmailEditText.getText().toString(), mPassWordEditText.getText().toString());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Response<Void> response, Retrofit retrofit) {
                        if (response.code() == 400) {
                            Log.w("Login", "Failed: Username or password is blank");
                            mErrorText.setText("Username and/or password is blank.");
                            animateWrongCredentials();
                        } else if (response.code() == 401) {
                            mErrorText.setText("Wrong credentials.");
                            Log.w("Login", "Failed: Wrong credentials");
                            animateWrongCredentials();
                        } else if (response.code() == 200) {
                            Log.i("Login", "Success: " + response.body());
                            NaviiPreferenceData.setLoggedInUserEmail(mEmailEditText.getText().toString());

                            Intent mainIntent = new Intent(parentActivity, MainActivity.class);
                            startActivity(mainIntent);
                        } else {
                            Log.w("Login", "Failed: WTF did we get. Response code: " + String.valueOf(response.code()));
                            mErrorText.setText("Internal server error.");
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.w("Login", "Failed: " + t.getMessage());
                    }
                });
            }
        });

        return v;
    }

    private void animateWrongCredentials() {
        YoYo.with(Techniques.Shake)
                .duration(700)
                .playOn(parentActivity.findViewById(R.id.intro_page5_edit_text_linear_layout));
    }
}
