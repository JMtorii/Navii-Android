package com.teamawesome.navii.fragment.intro;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.squareup.okhttp.ResponseBody;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.IntroActivity;
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
    private BootstrapEditText mpassWordEditText;


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
        mpassWordEditText = (BootstrapEditText) v.findViewById(R.id.intro_page5_password_edittext);


        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = parentActivity.userAPI.signUp(mEmailEditText.getText().toString(), mpassWordEditText.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Response response, Retrofit retrofit) {
                        if (response.code() == 400) {
                            Log.w("Sign Up", "Failed: Username or password is blank");
                        } else if (response.code() == 409) {
                            Log.w("Sign Up", "Failed: User already exists");
                        } else {
                            NaviiPreferenceData.setLoggedInUsername(mEmailEditText.getText().toString());

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
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.w("Sign Up", "Failed: " + t.getMessage());
                    }
                });
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: check if user exists in the server and username still exists
                NaviiPreferenceData.setLoggedInUsername(mEmailEditText.getText().toString());
            }
        });

        return v;
    }
}
