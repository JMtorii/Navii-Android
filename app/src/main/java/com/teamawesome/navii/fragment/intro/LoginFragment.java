package com.teamawesome.navii.fragment.intro;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.SignUpActivity;

import java.lang.reflect.Field;

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

        setTypefaceToInputLayout(usernameInputLayout, FONT_LOCATION);
        setTypefaceToInputLayout(passwordInputLayout, FONT_LOCATION);

        return view;
    }

    // TODO: Move to a helper method
    private void setTypefaceToInputLayout(TextInputLayout inputLayout, String typeFace){
        final Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), typeFace);
        if (inputLayout != null && inputLayout.getEditText() != null) {
            inputLayout.getEditText().setTypeface(tf);
            try {
                // Retrieve the CollapsingTextHelper Field
                final Field collapsingTextHelperField = inputLayout.getClass().getDeclaredField("mCollapsingTextHelper");
                collapsingTextHelperField.setAccessible(true);

                // Retrieve an instance of CollapsingTextHelper and its TextPaint
                final Object collapsingTextHelper = collapsingTextHelperField.get(inputLayout);
                final Field tpf = collapsingTextHelper.getClass().getDeclaredField("mTextPaint");
                tpf.setAccessible(true);

                // Apply your Typeface to the CollapsingTextHelper TextPaint
                ((TextPaint) tpf.get(collapsingTextHelper)).setTypeface(tf);
            } catch (Exception ignored) {
                // Nothing to do
            }
        }
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
