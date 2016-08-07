package com.teamawesome.navii.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.widget.Toast;

import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.User;
import com.teamawesome.navii.server.model.VoyagerResponse;
import com.teamawesome.navii.util.AnalyticsManager;
import com.teamawesome.navii.util.HashingAlgorithm;
import com.teamawesome.navii.util.NaviiPreferenceData;
import com.teamawesome.navii.util.RestClient;
import com.teamawesome.navii.util.ToolbarConfiguration;
import com.teamawesome.navii.util.ViewUtilities;
import com.teamawesome.navii.views.MainLatoButton;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

        attemptSignUp(email, username, hashedPassword);
    }

    private void attemptSignUp(final String email, final String username, final String hashedPassword) {
        User user = new User.Builder().email(email).username(username).password(hashedPassword).build();
        Observable<VoyagerResponse> call = RestClient.userAPI.createUser(user);
        call.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<VoyagerResponse>() {
                @Override
                public void onCompleted() {
                   // Nothing to do here
                    AnalyticsManager.getMixpanel().track("SignUpActivity - Successful email sign up");
                }

                @Override
                public void onError(Throwable throwable) {
                    String errorMessage;
                    if (throwable instanceof HttpException) {
                        errorMessage = getResources().getString(R.string.error_signup_validation);
                    } else if (throwable instanceof IOException) {
                        errorMessage = getResources().getString(R.string.error_network);
                    } else {
                        errorMessage = getResources().getString(R.string.error_unknown);
                    }

                    new AlertDialog.Builder(new ContextThemeWrapper(SignUpActivity.this, R.style.DialogTheme))
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
                    NaviiPreferenceData.createLoginSession(response.getUser().getUsername(), response.getUser().getEmail(), response.getToken());
                    Intent intent = new Intent(getApplicationContext(), ThankYouActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
    }
}
