package com.teamawesome.navii.fragment.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.IntroActivity;
import com.teamawesome.navii.util.Constants;

/**
 * Created by JMtorii on 15-09-22.
 */
public class IntroPageFiveFragment extends IntroAbstractPageFragment {

    private IntroActivity parentActivity;
    private BootstrapButton mSignUpButton;
    private BootstrapButton mLoginButton;
    private BootstrapEditText mEmailEditText;


    public static IntroPageFiveFragment newInstance(int position) {
        IntroPageFiveFragment f = new IntroPageFiveFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (IntroActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_intro_page5, container, false);
        mSignUpButton = (BootstrapButton) v.findViewById(R.id.intro_page5_sign_up_button);
        mLoginButton = (BootstrapButton) v.findViewById(R.id.intro_page5_login_button);
        mEmailEditText = (BootstrapEditText) v.findViewById(R.id.intro_page5_email_edittext);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntroPaymentFragment fragment = new IntroPaymentFragment();
                parentActivity.switchFragment(
                        fragment,
                        Constants.NO_ANIM,
                        Constants.NO_ANIM,
                        Constants.INTRO_PAYMENT_FRAGMENT_TAG,
                        true,
                        true,
                        true
                );
            }
        });

        // TODO: this ain't working completely yet.
        mEmailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // I'm so sorry for this Gods. I'll see Satan for this.
                if (hasFocus) {
                    getActivity().findViewById(R.id.intro_page_indicator).setVisibility(View.INVISIBLE);
                } else {
                    getActivity().findViewById(R.id.intro_page_indicator).setVisibility(View.VISIBLE);
                }
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }
}
