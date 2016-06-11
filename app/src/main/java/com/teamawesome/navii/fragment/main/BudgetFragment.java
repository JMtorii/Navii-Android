package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.BudgetAdapter;
import com.teamawesome.navii.views.MainLatoEditText;
import com.teamawesome.navii.views.MainLatoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Ian on 5/30/2016.
 */
public class BudgetFragment extends NaviiFragment {
    @BindView(R.id.budget_text)
    MainLatoEditText mainLatoEditText;

    @BindView(R.id.digit_pad)
    RecyclerView r;

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

        ButterKnife.bind(this,v);

        mainLatoEditText.setKeyListener(null);

        r.setLayoutParams(padHeight);
        RecyclerView.Adapter b = new BudgetAdapter(this, metrics);
        GridLayoutManager g = new GridLayoutManager(r.getContext(), 3);
        r.setAdapter(b);
        r.setLayoutManager(g);

        return v;
    }
}
