package com.teamawesome.navii.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamawesome.navii.R;

/**
 * Created by jtorii on 16-05-13.
 */
public class Fragment1 extends Fragment {

    private TextView mPartOneTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.part_one_fragment, container, false);
        mPartOneTextView = (TextView) v.findViewById(R.id.part_one_text_view);

        return v;
    }
}
