package com.teamawesome.navii.util;


import android.content.Context;
import android.util.AttributeSet;

import com.teamawesome.navii.views.MainLatoButton;

/**
 * Created by Ian on 5/30/2016.
 */
public class NaviiBudgetButton extends MainLatoButton {
    private int digit;

    public NaviiBudgetButton(Context context) {
        super(context);
    }

    public NaviiBudgetButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NaviiBudgetButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getDigit() {
        return digit;
    }

    public void setDigit(int digit){
        this.digit = digit;
    }
}
