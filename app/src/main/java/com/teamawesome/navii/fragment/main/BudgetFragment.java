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

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Ian on 5/30/2016.
 */
public class BudgetFragment extends NaviiParallaxFragment {
    @BindView(R.id.budget_text)
    MainLatoEditText budgetEditText;

    @BindView(R.id.digit_pad)
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_budget, container, false);
        ButterKnife.bind(this,v);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        RelativeLayout.LayoutParams padHeight = new RelativeLayout.LayoutParams(metrics.widthPixels,(int)(metrics.heightPixels * 0.4));
        padHeight.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        budgetEditText.setKeyListener(null);

        RecyclerView.Adapter adapter = new BudgetAdapter(this, metrics.heightPixels, metrics.widthPixels);
        GridLayoutManager gridLayout = new GridLayoutManager(recyclerView.getContext(), 3);
        recyclerView.setLayoutParams(padHeight);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayout);

        return v;
    }
}
