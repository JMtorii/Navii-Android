package com.teamawesome.navii.fragment.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.SignUpActivity;
import com.teamawesome.navii.util.ViewUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JMtorii on 16-07-25.
 */
public class LoginFragment extends Fragment {
    @BindView(R.id.login_logo_image_view)
    ImageView logoImageView;

    @BindView(R.id.login_username_input_layout)
    TextInputLayout usernameInputLayout;

    @BindView(R.id.login_username_edit_text)
    TextInputEditText usernameEditText;

    @BindView(R.id.login_password_input_layout)
    TextInputLayout passwordInputLayout;

    @BindView(R.id.login_password_edit_text)
    TextInputEditText passwordEditText;

    @BindView(R.id.login_email_button)
    LinearLayout loginEmailButton;

    @BindView(R.id.login_sign_up_button)
    Button signUpButton;

    private static final String FONT_LOCATION = "fonts/Lato-Regular.ttf";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        ViewUtilities.setTypefaceToInputLayout(getActivity(), usernameInputLayout, FONT_LOCATION);
        ViewUtilities.setTypefaceToInputLayout(getActivity(), passwordInputLayout, FONT_LOCATION);

        return view;
    }

    @OnClick(R.id.login_email_button)
    public void emailButtonPressed() {
        Log.i(this.getClass().getName(), "Email login button pressed");
    }

    @OnClick(R.id.login_sign_up_button)
    public void signUpButtonPressed() {
        Log.i(this.getClass().getName(), "Sign up button pressed");

        Intent nextActivity = new Intent(getActivity(), SignUpActivity.class);
        startActivity(nextActivity);
        getActivity().overridePendingTransition(R.anim.slide_in_down, R.anim.hold);
    }
}
