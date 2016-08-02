package com.teamawesome.navii.activity;

import android.app.Activity;
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
    private Activity mContext = this;

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
        String username = "", email = "", password = "";
                username = nameEditText.getText().toString().trim();
                nameEditText.setText(username);
        email = emailEditText.getText().toString().trim();
        emailEditText.setText(email);
        password = passwordEditText.getText().toString();

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
            attemptSignup(email, hashedPassword);
        } catch (Exception e) {
            // Failed to hash password
        }
    }

    private void attemptSignup(final String email, final String hashedPassword) {
        User user = new User.Builder().email(email).password(hashedPassword).build();
        Call<Void> call = RestClient.userAPI.createUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    // TODO: Handle this?
                    //attemptLogin(email, hashedPassword);
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
}
