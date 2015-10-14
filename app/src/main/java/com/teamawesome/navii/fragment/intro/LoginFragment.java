package com.teamawesome.navii.fragment.intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.IntroActivity;

/**
 * Created by JMtorii on 15-08-25.
 */
public class LoginFragment extends Fragment {
    EditText mEmailText;
    EditText mPasswordText;
    Button mLoginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        mEmailText = (EditText) v.findViewById(R.id.login_email);
        mPasswordText = (EditText) v.findViewById(R.id.login_password);
        mLoginButton = (Button) v.findViewById(R.id.login_submit_button);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform check, submit
                IntroActivity activity = (IntroActivity) getActivity();
                // TODO: William: load necessary fragment
//                activity.loadFragment();
            }
        });

        return v;
    }
}
