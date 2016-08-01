package com.teamawesome.navii.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.teamawesome.navii.NaviiApplication;
import com.teamawesome.navii.R;
import com.teamawesome.navii.server.api.LoginAPI;
import com.teamawesome.navii.server.model.User;
import com.teamawesome.navii.util.HashingAlgorithm;
import com.teamawesome.navii.util.RestClient;
import com.teamawesome.navii.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ecrothers on 07/31/16.
 */
public class LoginActivity extends Activity {
    Activity mContext = this;
    private static String loggingTag = "Login Activity";

    private CallbackManager callbackManager;

    private Button signupButton;
    private LoginButton facebookLoginButton;
    private EditText usernameText, emailText, passwordText;
    private TextView tosText, loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        findWidgets(this);
        setupSignupButton();
        setupFacebookLogin();
        setupTextClickables();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void findWidgets(Activity activity) {
        signupButton = (Button) activity.findViewById(R.id.email_sign_up_button);
        facebookLoginButton = (LoginButton) activity.findViewById(R.id.facebook_login_button);
        usernameText = (EditText) activity.findViewById(R.id.username_entry);
        emailText = (EditText) activity.findViewById(R.id.email_entry);
        passwordText = (EditText) activity.findViewById(R.id.password_entry);
        tosText = (TextView) activity.findViewById(R.id.creation_ToS);
        loginText = (TextView) activity.findViewById(R.id.login);
    }

    private void setupSignupButton() {
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = "", email = "", password = "";
                username = usernameText.getText().toString().trim();
                usernameText.setText(username);
                email = emailText.getText().toString().trim();
                emailText.setText(email);
                password = passwordText.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(mContext.getApplicationContext(), "Signup failed: You must provide a login name.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                CharSequence inputStr = email;
                Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(inputStr);
                if (!matcher.matches()) {
                    Toast.makeText(mContext.getApplicationContext(), "Signup failed: Not a valid email.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 5) {
                    Toast.makeText(mContext.getApplicationContext(), "Signup failed: Password not strong enough.", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashingAlgorithm ha = new HashingAlgorithm();
                try {
                    String hashedPassword = ha.sha256(password);
                    attemptSignup(username, email, hashedPassword);
                } catch (Exception e) {
                    Log.e(loggingTag, "Failed to hash password");
                }
            }
        });
    }

    private void setupFacebookLogin() {
        List<String> permissionNeeds = Arrays.asList("public_profile", "email");
        facebookLoginButton.setReadPermissions(permissionNeeds);

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
                Toast.makeText(mContext.getApplicationContext(), "Error while attempting to login.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupTextClickables() {
        tosText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [EC] TODO: Add terms of service
                /*DialogFragment tosDialogFragment = TosDialogFragment.newInstance();
                tosDialogFragment.show(getFragmentManager(), "tosDialog");*/
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = "", email = "", password = "";
                username = usernameText.getText().toString().trim();
                usernameText.setText(username);
                email = emailText.getText().toString().trim();
                emailText.setText(email);
                password = passwordText.getText().toString();

                HashingAlgorithm ha = new HashingAlgorithm();
                try {
                    String hashedPassword = ha.sha256(password);
                    attemptLogin(username, email, hashedPassword);
                } catch (Exception e) {
                    Log.e(loggingTag, "Failed to hash password");
                }
            }
        });
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
                    Log.e(loggingTag, "Signup failed");
                    Toast.makeText(mContext.getApplicationContext(), "Signup failed...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(loggingTag, "Failed to create user");
                Log.e(loggingTag, t.getMessage().toString());
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
                    Log.e(loggingTag, "Login failed");
                    Toast.makeText(mContext.getApplicationContext(), "Login failed...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(loggingTag, "Login attempt failed");
                Log.e(loggingTag, t.getMessage().toString());
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
                    Log.e(loggingTag, "Login failed");
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
                Log.e(loggingTag, "Login attempt failed");
                Log.e(loggingTag, t.getMessage().toString());
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
}
