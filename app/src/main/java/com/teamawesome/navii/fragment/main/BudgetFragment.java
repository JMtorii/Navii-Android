package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.BudgetAdapter;
import com.teamawesome.navii.views.MainLatoEditText;

import butterknife.ButterKnife;

/**
 * Created by Ian on 5/30/2016.
 */
public class BudgetFragment extends NaviiFragment {
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        RelativeLayout.LayoutParams padHeight = new RelativeLayout.LayoutParams(metrics.widthPixels
                ,(int)(metrics.heightPixels*0.4));

        padHeight.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        View v = inflater.inflate(R.layout.fragment_budget, container, false);

        MainLatoEditText mainLatoEditText = (MainLatoEditText) v.findViewById(R.id.budget_text);
        mainLatoEditText.setOnClickListener(null);
        mainLatoEditText.setClickable(false);

        RecyclerView r = (RecyclerView) v.findViewById(R.id.digit_pad);
        r.setLayoutParams(padHeight);
        RecyclerView.Adapter b = new BudgetAdapter(metrics);
        GridLayoutManager g = new GridLayoutManager(r.getContext(), 3);
        r.setAdapter(b);
        r.setLayoutManager(g);
        return v;
    }
}
