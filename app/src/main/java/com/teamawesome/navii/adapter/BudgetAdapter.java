package com.teamawesome.navii.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.main.NaviiFragment;
import com.teamawesome.navii.util.NaviiBudgetButton;
import com.teamawesome.navii.views.MainLatoEditText;
import com.teamawesome.navii.views.ParallaxViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ian on 5/31/2016.
 */
public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    int screenWidth;
    int screenHeight;
    static NaviiFragment nf;

    public BudgetAdapter(NaviiFragment f, DisplayMetrics metrics) {
        super();
        nf = f;
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
                Drawable img = nf.getResources().getDrawable(R.drawable.ic_backspace_black_48dp, null);
                holder.budgetButton.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                holder.budgetButton.setDigit(9);
                break;
            case 10:
                holder.budgetButton.setText(String.valueOf(0));
                holder.budgetButton.setDigit(0);
                break;
            case 11:
                Drawable img2 = nf.getResources().getDrawable(R.drawable.ic_check_circle_black_48dp, null);
                holder.budgetButton.setCompoundDrawablesWithIntrinsicBounds(null, img2, null, null);
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


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick (R.id.budget_button)
        public void pressed(View v){
            if (budgetButton.getDigit() == 9){
                backspace(v);
            }
            else if (budgetButton.getDigit() == 11){
                next(v);
            }
            else {
                View parent = v.getRootView();
                MainLatoEditText latoEditText =
                        (MainLatoEditText) parent.findViewById(R.id.budget_text);
                String newText = latoEditText.getText().toString() + budgetButton.getDigit();
                latoEditText.setText(newText);
            }
        }

        private void backspace(View v){
            View parent = v.getRootView();
            MainLatoEditText latoEditText =
                    (MainLatoEditText) parent.findViewById(R.id.budget_text);
            StringBuilder newText = new StringBuilder(latoEditText.getText());
            if (newText.length() > 1) {
                newText.deleteCharAt(newText.length() - 1);
                latoEditText.setText(newText);
            }
        }

        private void next(View v){
            View parent = v.getRootView();
            Button mNextButton = (Button) parent.findViewById(R.id.main_next_button);
            ParallaxViewPager p = (ParallaxViewPager) nf.getActivity().findViewById(R.id.main_view_pager);
            p.setCurrentItem(p.getCurrentItem() + 1, true);
            mNextButton.setVisibility(View.VISIBLE);
        }


    }
}
