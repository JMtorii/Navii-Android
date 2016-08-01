package com.teamawesome.navii.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.squareup.okhttp.ResponseBody;
import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.User;
import com.teamawesome.navii.util.RestClient;
import com.teamawesome.navii.util.SessionManager;
import com.teamawesome.navii.util.ToolbarConfiguration;
import com.teamawesome.navii.util.ViewUtilities;
import com.teamawesome.navii.views.MainLatoButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by JMtorii on 16-07-30.
 */
public class SignUpActivity extends NaviiToolbarActivity {
    private static final String FONT_LOCATION = "fonts/Lato-Regular.ttf";
    Activity mContext = this;

    @BindView(R.id.sign_up_name_input_layout)
    TextInputLayout nameInputLayout;

    @BindView(R.id.sign_up_name_edit_text)
    TextInputEditText nameEditText;

    @BindView(R.id.sign_up_email_input_layout)
    TextInputLayout emailInputLayout;

    @BindView(R.id.sign_up_email_edit_text)
    TextInputEditText emailEditText;

    @BindView(R.id.sign_up_password_input_layout)
    TextInputLayout passwordInputLayout;

    @BindView(R.id.sign_up_password_edit_text)
    TextInputEditText passwordEditText;

    @BindView(R.id.sign_up_password_again_input_layout)
    TextInputLayout passwordAgainInputLayout;

    @BindView(R.id.sign_up_password_again_edit_text)
    TextInputEditText passwordAgainEditText;

    @BindView(R.id.sign_up_button)
    MainLatoButton signUpButton;

    @Override
    public ToolbarConfiguration getToolbarConfiguration() {
        return ToolbarConfiguration.SignUp;
    }

    @Override
    public void onLeftButtonClick() {
        // Nothing to do here
    }

    private void attemptSignup(final String username, final String email, final String hashedPassword) {
        User user = new User.Builder().username(username).email(email).password(hashedPassword).build();
        Call<Void> call = RestClient.userAPI.createUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    attemptLogin(username, email, hashedPassword);
                } else {
                    Toast.makeText(mContext.getApplicationContext(), "Signup failed...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext.getApplicationContext(), "Server down, try again later...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void attemptLogin(final String username, final String email, final String hashedPassword) {
        User user = new User.Builder().username(username).email(email).password(hashedPassword).build();
        Call<ResponseBody> call = RestClient.loginAPI.attemptLogin(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    try {
                        String token = response.body().string();
                        Log.i("tok", token);
                        loginUserComplete(username, email, token);
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

    private void loginUserComplete(String user, String email, String token) {
        SessionManager.createLoginSession(user, email, token);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onRightButtonClick() {
        onModalBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        ViewUtilities.setTypefaceToInputLayout(this, nameInputLayout, FONT_LOCATION);
        ViewUtilities.setTypefaceToInputLayout(this, emailInputLayout, FONT_LOCATION);
        ViewUtilities.setTypefaceToInputLayout(this, passwordInputLayout, FONT_LOCATION);
        ViewUtilities.setTypefaceToInputLayout(this, passwordAgainInputLayout, FONT_LOCATION);
    }
}
