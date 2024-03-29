package com.teamawesome.navii.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.fragment.main.BudgetFragment;
import com.teamawesome.navii.util.NaviiBudgetButton;
import com.teamawesome.navii.views.MainLatoEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ian on 5/31/2016.
 */
public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {
    private int screenWidth;
    private int screenHeight;
    private static BudgetFragment budgetFragment;

    public BudgetAdapter(BudgetFragment fragment, int heightPixels, int widthPixels) {
        super();
        budgetFragment = fragment;
        this.screenWidth = widthPixels;
        this.screenHeight = heightPixels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View buttonView = inflater.inflate(R.layout.budget_button, parent, false);
        return new ViewHolder(buttonView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        double buttonHeight = Math.ceil((screenHeight * 0.4) / 4);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(screenWidth / 3, (int) buttonHeight);
        holder.budgetButton.setLayoutParams(params);
        double padHeight = (buttonHeight/2) + 4;
        switch (position){
            case 9:
                Drawable img = budgetFragment.getResources().getDrawable(R.drawable.ic_backspace, null);
                holder.budgetButton.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                if (img != null) {
                    holder.budgetButton.setPadding(0,(int)padHeight - img.getIntrinsicHeight()/2,0,0);
                }
                holder.budgetButton.setDigit(-1);
                break;
            case 10:
                holder.budgetButton.setText(String.valueOf(0));
                holder.budgetButton.setDigit(0);
                break;
            case 11:
                Drawable img2 = budgetFragment.getResources().getDrawable(R.drawable.ic_check_circle, null);
                holder.budgetButton.setCompoundDrawablesWithIntrinsicBounds(null, img2, null, null);
                if (img2 != null) {
                    holder.budgetButton.setPadding(0,(int)padHeight - img2.getIntrinsicHeight()/2,0,0);
                }
                holder.budgetButton.setDigit(11);
                break;
            default:
                holder.budgetButton.setText(String.valueOf(position + 1));
                holder.budgetButton.setDigit(position + 1);
                break;
        }
    }

    @Override
    public int getItemCount() {
        //Custom keyboard is 12 buttons
        return 12;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.budget_button)
        NaviiBudgetButton budgetButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick (R.id.budget_button)
        public void pressed(View view){
            if (budgetButton.getDigit() == -1){
                backspace(view);
            } else if (budgetButton.getDigit() == 11){
                next(view);
            } else {
                addDigit(view);
            }
        }

        public void backspace(View view){
            View parent = view.getRootView();
            MainLatoEditText latoEditText = (MainLatoEditText) parent.findViewById(R.id.budget_text);
            StringBuilder newText = new StringBuilder(latoEditText.getText());
            if (newText.length() > 1) {
                newText.deleteCharAt(newText.length() - 1);
                latoEditText.setText(newText);
            }
        }

        public void next(View view){
            View parent = view.getRootView();
            MainLatoEditText latoEditText = (MainLatoEditText) parent.findViewById(R.id.budget_text);
            if (latoEditText.getText().toString().length() == 1){
                Toast.makeText(budgetFragment.getActivity(), "Invalid", Toast.LENGTH_SHORT).show();
            }
            else {
                MainActivity mainActivity = (MainActivity) budgetFragment.getActivity();
                mainActivity.nextPress(parent);
            }
        }

        public void addDigit(View view){
            View parent = view.getRootView();
            MainLatoEditText latoEditText = (MainLatoEditText) parent.findViewById(R.id.budget_text);
            if (budgetButton.getDigit() == 0 && latoEditText.getText().length() == 1){
                Toast.makeText(budgetFragment.getActivity(), "Invalid", Toast.LENGTH_SHORT).show();
            }
            else {
                String newText = latoEditText.getText().toString() + budgetButton.getDigit();
                latoEditText.setText(newText);
            }
        }
    }
}
