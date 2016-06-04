package com.teamawesome.navii.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.teamawesome.navii.R;
import com.teamawesome.navii.util.NaviiBudgetButton;
import com.teamawesome.navii.views.LatoType;
import com.teamawesome.navii.views.MainLatoEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        switch (position){
            case 9:
                holder.budgetButton.setText("DEL");
                holder.budgetButton.setDigit(9);
                break;
            case 10:
                holder.budgetButton.setText(String.valueOf(0));
                holder.budgetButton.setDigit(0);
                break;
            case 11:
                holder.budgetButton.setText("NXT");
                holder.budgetButton.setDigit(11);
                break;
            default:
                holder.budgetButton.setText(String.valueOf(position + 1));
                holder.budgetButton.setDigit(position + 1);
                break;
        }

        int buttonHeight = ((int) (screenHeight*0.4))/4;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(screenWidth/3,
                buttonHeight);
        holder.budgetButton.setLayoutParams(params);

    }

    @Override
    public int getItemCount() {
        //Custom keyboard is 12 buttons
        return 12;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.budget_button)
        NaviiBudgetButton budgetButton;

        @OnClick (R.id.budget_button)
        public void pressed(View v){
            if (budgetButton.getDigit() == 9){
                backspace(v);
            }
            else if (budgetButton.getDigit() == 11){
                //nothing yet
            }
            else {
                View parent = v.getRootView();
                MainLatoEditText latoEditText =
                        (MainLatoEditText) parent.findViewById(R.id.budget_text);
                String newText = latoEditText.getText().toString() + budgetButton.getDigit();
                latoEditText.setText(newText);
            }
        }

        public void backspace(View v){
            View parent = v.getRootView();
            MainLatoEditText latoEditText =
                    (MainLatoEditText) parent.findViewById(R.id.budget_text);
            StringBuilder newText = new StringBuilder(latoEditText.getText());
            if (newText.length() > 1) {
                newText.deleteCharAt(newText.length() - 1);
                latoEditText.setText(newText);
            }
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
