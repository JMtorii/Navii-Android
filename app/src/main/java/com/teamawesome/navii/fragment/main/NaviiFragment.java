package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.teamawesome.navii.activity.IntroActivity;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.activity.NaviiActivity;

/**
 * Created by JMtorii on 15-12-25.
 */
public class NaviiFragment extends Fragment {
    protected NaviiActivity parentActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parentActivity = getActivity().getClass().equals(MainActivity.class) ?
                (MainActivity) getActivity() :
                (IntroActivity) getActivity();
    }
}
