package com.teamawesome.navii.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.IntroActivity;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.util.Constants;

/**
 * Created by JMtorii on 15-09-22.
 */
public class IntroPageFiveFragment extends Fragment {
    private static final String ARG_POSITION = "position";

    private int mPosition;
    private IntroActivity parentActivity;
    private Button mSignUpButton;
    private Button mSignInButton;


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
        mPosition = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_intro_page5, container, false);
        mSignUpButton = (Button) v.findViewById(R.id.intro_page5_sign_up_button);
        mSignInButton = (Button) v.findViewById(R.id.intro_page5_sign_in_button);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntroPaymentFragment fragment = new IntroPaymentFragment();
                parentActivity.switchFragment(
                        fragment,
                        Constants.NO_ANIM,
                        Constants.NO_ANIM,
                        "",
                        true,
                        true,
                        true
                );
            }
        });

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntroActivity activity = (IntroActivity) getActivity();
                Fragment fragment = new LoginFragment();
                activity.loadFragment(fragment, true);
            }
        });

        return v;
    }
}
