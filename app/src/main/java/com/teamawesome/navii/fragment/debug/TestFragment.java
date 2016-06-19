package com.teamawesome.navii.fragment.debug;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.main.NaviiParallaxFragment;

/**
 * Created by JMtorii on 15-07-02.
 */
public class TestFragment extends NaviiParallaxFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }
}
