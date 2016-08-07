package com.teamawesome.navii.fragment.intro;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.teamawesome.navii.activity.SignUpActivity;
import com.teamawesome.navii.server.model.User;
import com.teamawesome.navii.server.model.VoyagerResponse;
import com.teamawesome.navii.util.HashingAlgorithm;
import com.teamawesome.navii.util.NaviiPreferenceData;
import com.teamawesome.navii.util.RestClient;
import com.teamawesome.navii.util.ViewUtilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JMtorii on 16-07-25.
 */
public class LoginFragment extends Fragment {
    @BindView(R.id.login_logo_image_view)
    ImageView logoImageView;

    @BindView(R.id.login_email_input_layout)
    TextInputLayout emailLoginInputLayout;

    @BindView(R.id.login_email_edit_text)
    TextInputEditText emailLoginEditText;

    @BindView(R.id.login_password_input_layout)
    TextInputLayout passwordInputLayout;

    @BindView(R.id.login_password_edit_text)
    TextInputEditText passwordEditText;

    @BindView(R.id.login_email_button)
    LinearLayout loginEmailButton;

    @BindView(R.id.login_sign_up_button)
    Button signUpButton;

    @BindView(R.id.facebook_login_button)
    LoginButton facebookLoginButton;

    private static final String FONT_LOCATION = "fonts/Lato-Regular.ttf";
    private CallbackManager callbackManager;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        ViewUtilities.setTypefaceToInputLayout(getActivity(), emailLoginInputLayout, FONT_LOCATION);
        ViewUtilities.setTypefaceToInputLayout(getActivity(), passwordInputLayout, FONT_LOCATION);
        setupFacebookLogin();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.login_email_button)
    public void emailButtonPressed() {
        Log.i(this.getClass().getName(), "Email login button pressed");

        String email = emailLoginEditText.getText().toString().trim();
        emailLoginEditText.setText(email);
        String password = passwordEditText.getText().toString();

        try {
            String hashedPassword = HashingAlgorithm.sha256(password);
            attemptLogin(email, hashedPassword);
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Login failed, invalid password", Toast.LENGTH_SHORT).show();
        }
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

        Observable<VoyagerResponse> call = RestClient.loginAPI.attemptLogin(user);
        call.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<VoyagerResponse>() {
                @Override
                public void onCompleted() {
                    // Nothing to do here
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();

                    String errorMessage;
                    if (throwable instanceof HttpException) {
                        errorMessage = getResources().getString(R.string.error_login_validation);
                    } else if (throwable instanceof IOException) {
                        errorMessage = getResources().getString(R.string.error_network);
                    } else {
                        errorMessage = getResources().getString(R.string.error_unknown);
                    }

                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.DialogTheme))
                            .setTitle(getResources().getString(R.string.error_dialog_title))
                            .setMessage(errorMessage)
                            .setPositiveButton(getResources().getString(R.string.error_okay), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Nothing to do here
                                }
                            })
                            .show();
                }

                @Override
                public void onNext(VoyagerResponse response) {
//                        String token = responseBody.string();
//                        Log.i("tok", token);
                        loginUserComplete(response.getUser().getUsername(), response.getUser().getEmail(), response.getToken());
                }
            });
    }

    private void setupFacebookLogin() {
        List<String> permissionNeeds = Arrays.asList("public_profile", "email");
        facebookLoginButton.setReadPermissions(permissionNeeds);
        facebookLoginButton.setFragment(this);

        // Callback registration
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                attemptFacebookLogin(AccessToken.getCurrentAccessToken().getToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getActivity().getApplicationContext(), "Error while attempting to login.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void attemptFacebookLogin(final String facebookToken) {
        Observable<ResponseBody> call = RestClient.loginAPI.attemptFacebookLogin(facebookToken);
        call.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {
                    // Nothing to do here
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.DialogTheme))
                            .setTitle(getResources().getString(R.string.error_dialog_title))
                            .setMessage("Server down, try again later...")
                            .setPositiveButton(getResources().getString(R.string.error_okay), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Nothing to do here
                                }
                            })
                            .show();
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

                @Override
                public void onNext(ResponseBody response) {
                    try {
                        final String token = response.string();
                        // TODO: currently uses user information obtained from Facebook, may want to obtain user information from our own server
                        GraphRequest request = GraphRequest.newMeRequest(
                                AccessToken.getCurrentAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            loginUserComplete(object.getString("name"), object.getString("email"), token);
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
                }
            });
    }

    /**
     * On successful login, start a new session and begin the main activity
     */
    private void loginUserComplete(String fullName, String email, String token) {
        NaviiPreferenceData.createLoginSession(fullName, email, token);
        Intent nextActivity = new Intent(getActivity(), MainActivity.class);
        startActivity(nextActivity);
        getActivity().finish();
    }
}
