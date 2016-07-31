package com.teamawesome.navii.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextPaint;

import com.teamawesome.navii.R;
import com.teamawesome.navii.util.ToolbarConfiguration;
import com.teamawesome.navii.views.MainLatoButton;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        setTypefaceToInputLayout(nameInputLayout, FONT_LOCATION);
        setTypefaceToInputLayout(emailInputLayout, FONT_LOCATION);
        setTypefaceToInputLayout(passwordInputLayout, FONT_LOCATION);
        setTypefaceToInputLayout(passwordAgainInputLayout, FONT_LOCATION);
    }

    // TODO: Move to a helper method
    private void setTypefaceToInputLayout(TextInputLayout inputLayout, String typeFace){
        final Typeface tf = Typeface.createFromAsset(getAssets(), typeFace);
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
}
