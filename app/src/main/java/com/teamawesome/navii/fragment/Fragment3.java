package com.teamawesome.navii.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamawesome.navii.R;

/**
 * Created by jtorii on 16-05-17.
 */
public class Fragment3 extends Fragment {

    private TextView mPartThreeTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.part_three_fragment, container, false);
        mPartThreeTextView = (TextView) v.findViewById(R.id.part_three_text_view);

        return v;
    }
}