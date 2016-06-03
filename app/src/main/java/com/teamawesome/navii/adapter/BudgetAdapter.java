package com.teamawesome.navii.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.teamawesome.navii.R;
import com.teamawesome.navii.util.NaviiBudgetButton;

/**
 * Created by Ian on 5/31/2016.
 */
public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    int screenWidth;
    int screenHeight;
    public BudgetAdapter(DisplayMetrics metrics) {
        super();
        this.screenWidth = metrics.widthPixels;
        this.screenHeight = metrics.heightPixels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View buttonView = inflater.inflate(R.layout.budget_button, parent, false);
        ViewHolder viewHolder = new ViewHolder(buttonView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BudgetAdapter.ViewHolder holder, int position) {
        if (position == 10){
            holder.budgetButton.setText(String.valueOf(0));
            holder.budgetButton.setDigit(0);
        }
        else {
            holder.budgetButton.setText(String.valueOf(position + 1));
            holder.budgetButton.setDigit(position + 1);
        }
        int buttonHeight = ((int) (screenHeight*0.4))/4;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(screenWidth/3,
                buttonHeight);
        holder.budgetButton.setLayoutParams(params);
        holder.budgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.budgetButton.pressed(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        //Custom keyboard is 12 buttons
        return 12;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public NaviiBudgetButton budgetButton;

        public ViewHolder(View itemView) {
            super(itemView);
            budgetButton =
                    (NaviiBudgetButton) itemView.findViewById(R.id.budget_button);

        }
    }
}
