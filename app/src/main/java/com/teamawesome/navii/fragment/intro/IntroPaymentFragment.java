package com.teamawesome.navii.fragment.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.IntroActivity;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.util.Constants;

/**
 * Created by JMtorii on 15-09-24.
 */
public class IntroPaymentFragment extends Fragment {
    private Button mNextButton;
    private IntroActivity parentActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (IntroActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_intro_payment, container, false);
        Log.i("IntroPaymentFragment","onCreateView");
        mNextButton = (Button) v.findViewById(R.id.intro_payment_next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                IntroPaymentFragment fragment = new IntroPaymentFragment();
//                parentActivity.switchFragment(
//                        fragment,
//                        Constants.NO_ANIM,
//                        Constants.NO_ANIM,
//                        "",
//                        true,
//                        true,
//                        true
//                );
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}
