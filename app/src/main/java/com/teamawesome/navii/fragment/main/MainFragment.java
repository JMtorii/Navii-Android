package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.teamawesome.navii.activity.MainActivity;

/**
 * Created by JMtorii on 2015-11-18.
 */
public class MainFragment extends Fragment {
    protected MainActivity parentActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (MainActivity) getActivity();
    }
}
