package com.teamawesome.navii.fragment.intro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.okhttp.ResponseBody;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.server.model.User;
import com.teamawesome.navii.util.HashingAlgorithm;
import com.teamawesome.navii.util.RestClient;
import com.teamawesome.navii.util.SessionManager;
import com.teamawesome.navii.util.ToolbarConfiguration;
import com.teamawesome.navii.util.ViewUtilities;
import com.teamawesome.navii.views.MainLatoButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.teamawesome.navii.activity.SignUpActivity;

import butterknife.OnClick;

/**
 * Created by JMtorii on 16-07-25.
 */
public class LoginFragment extends Fragment {
    Activity mContext;

    private CallbackManager callbackManager;

    @BindView(R.id.login_logo_image_view)
    ImageView logoImageView;

    @BindView(R.id.login_email_input_layout)
    TextInputLayout emailLoginInputLayout;

    @BindView(R.id.login_email_edit_text)
    TextInputEditText emailLoginEditText;

    /*@BindView(R.id.sign_up_email_input_layout)
    TextInputLayout emailInputLayout;

    @BindView(R.id.sign_up_email_edit_text)
    TextInputEditText emailEditText;*/

    @BindView(R.id.login_password_input_layout)
    TextInputLayout passwordInputLayout;

    @BindView(R.id.login_password_edit_text)
    TextInputEditText passwordEditText;

    @BindView(R.id.login_email_button)
    LinearLayout loginEmailButton;

    @BindView(R.id.login_sign_up_button)
    Button signUpButton;

    /*@BindView(R.id.facebook_login_button)
    LoginButton facebookLoginButton;*/

    private static final String FONT_LOCATION = "fonts/Lato-Regular.ttf";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        ViewUtilities.setTypefaceToInputLayout(getActivity(), emailLoginInputLayout, FONT_LOCATION);
        ViewUtilities.setTypefaceToInputLayout(getActivity(), passwordInputLayout, FONT_LOCATION);

        return view;
    }

    @OnClick(R.id.login_email_button)
    public void emailButtonPressed() {
        Log.i(this.getClass().getName(), "Email login button pressed");
        attemptLogin(emailLoginEditText.toString(), passwordEditText.toString());
    }

    @OnClick(R.id.login_sign_up_button)
    public void signUpButtonPressed() {
        Log.i(this.getClass().getName(), "Sign up button pressed");

        Intent nextActivity = new Intent(getActivity(), SignUpActivity.class);
        startActivity(nextActivity);
        getActivity().overridePendingTransition(R.anim.slide_in_down, R.anim.hold);
    }

    /*@OnClick(R.id.facebook_login_button)
    public void facebookLoginButtonPressed() {
        Log.i(this.getClass().getName(), "Facebook login button pressed");
    }*/

    private void attemptLogin(final String email, final String hashedPassword) {
        User user = new User.Builder().email(email).password(hashedPassword).build();
        Call<ResponseBody> call = RestClient.loginAPI.attemptLogin(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    try {
                        String token = response.body().string();
                        Log.i("tok", token);
                        loginUserComplete(email, token);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext.getApplicationContext(), "Login failed...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext.getApplicationContext(), "Server down, try again later...", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void setupFacebookLogin() {
//        List<String> permissionNeeds = Arrays.asList("public_profile", "email");
//        facebookLoginButton.setReadPermissions(permissionNeeds);
//
//        // Callback registration
//        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                attemptFacebookLogin(AccessToken.getCurrentAccessToken().getToken());
//            }
//
//            @Override
//            public void onCancel() {
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                Toast.makeText(mContext.getApplicationContext(), "Error while attempting to login.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void attemptFacebookLogin(final String facebookToken) {
        Call<ResponseBody> call = RestClient.loginAPI.attemptFacebookLogin(facebookToken);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    try {
                        final String token = response.body().string();
                        // TODO: currently uses user information obtained from Facebook, may want to obtain user information from our own server
                        GraphRequest request = GraphRequest.newMeRequest(
                                AccessToken.getCurrentAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        try {
                                            loginUserComplete(object.getString("email"), token);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext.getApplicationContext(), "Login failed...", Toast.LENGTH_SHORT).show();
                    if (AccessToken.getCurrentAccessToken() != null) {
                        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                                .Callback() {

                            @Override
                            public void onCompleted(GraphResponse graphResponse) {
                                LoginManager.getInstance().logOut();
                            }
                        }).executeAsync();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext.getApplicationContext(), "Server down, try again later...", Toast.LENGTH_SHORT).show();
                if (AccessToken.getCurrentAccessToken() != null) {
                    new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                            .Callback() {

                        @Override
                        public void onCompleted(GraphResponse graphResponse) {
                            LoginManager.getInstance().logOut();
                        }
                    }).executeAsync();
                }
            }
        });
    }

    /**
     * On successful login, start a new session and begin the main activity
     */
    private void loginUserComplete(String email, String token) {
        SessionManager.createLoginSession(email, token);
        Intent nextActivity = new Intent(mContext, MainActivity.class);
        startActivity(nextActivity);
        mContext.finish();
    }
}
