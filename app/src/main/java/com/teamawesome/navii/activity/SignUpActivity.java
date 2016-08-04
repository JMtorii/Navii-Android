package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.widget.Toast;

import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.User;
import com.teamawesome.navii.util.HashingAlgorithm;
import com.teamawesome.navii.util.RestClient;
import com.teamawesome.navii.util.ToolbarConfiguration;
import com.teamawesome.navii.util.ViewUtilities;
import com.teamawesome.navii.views.MainLatoButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by JMtorii on 16-07-30.
 */
public class SignUpActivity extends NaviiToolbarActivity {
    private static final String FONT_LOCATION = "fonts/Lato-Regular.ttf";

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

    @OnClick(R.id.sign_up_button)
    public void signUpButtonPressed() {
        Log.i(this.getClass().getName(), "Sign up button pressed");
        String username = nameEditText.getText().toString().trim();
        nameEditText.setText(username);
        String email = emailEditText.getText().toString().trim();
        emailEditText.setText(email);
        String password = passwordEditText.getText().toString();
        String passwordAgain = passwordAgainEditText.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Signup failed: You must provide a login name.", Toast.LENGTH_SHORT).show();
            return;
        }

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            Toast.makeText(getApplicationContext(), "Signup failed: Not a valid email.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 5) {
            Toast.makeText(getApplicationContext(), "Signup failed: Password not strong enough.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (passwordAgain.compareTo(password) != 0) {
            Toast.makeText(getApplicationContext(), "Signup failed: Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        String hashedPassword;
        try {
            hashedPassword = HashingAlgorithm.sha256(password);
        } catch (Exception e) {
            // Failed to hash password
            Toast.makeText(getApplicationContext(), "Signup failed: Invalid password.", Toast.LENGTH_SHORT).show();
            return;
        }

        attemptSignup(email, username, hashedPassword);
    }

    private void attemptSignup(final String email, final String username, final String hashedPassword) {
        User user = new User.Builder().email(email).username(username).password(hashedPassword).build();
        Call<Void> call = RestClient.userAPI.createUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    // TODO: Handle this?
                    //attemptLogin(email, hashedPassword);
                } else {
                    Toast.makeText(getApplicationContext(), "Signup failed...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Server down, try again later...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
