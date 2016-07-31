package com.teamawesome.navii.util;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.text.TextPaint;

import java.lang.reflect.Field;

/**
 * Created by JMtorii on 16-07-31.
 */
public class ViewUtilities {
    public static void setTypefaceToInputLayout(Activity activity, TextInputLayout inputLayout, String typeFace){
        final Typeface tf = Typeface.createFromAsset(activity.getAssets(), typeFace);
        if (inputLayout != null && inputLayout.getEditText() != null) {
            inputLayout.getEditText().setTypeface(tf);
            try {
                // Retrieve the CollapsingTextHelper Field
                final Field collapsingTextHelperField = inputLayout.getClass().getDeclaredField("mCollapsingTextHelper");
                collapsingTextHelperField.setAccessible(true);

                // Retrieve an instance of CollapsingTextHelper and its TextPaint
                final Object collapsingTextHelper = collapsingTextHelperField.get(inputLayout);
                final Field tpf = collapsingTextHelper.getClass().getDeclaredField("mTextPaint");
                tpf.setAccessible(true);

                // Apply your Typeface to the CollapsingTextHelper TextPaint
                ((TextPaint) tpf.get(collapsingTextHelper)).setTypeface(tf);
            } catch (Exception ignored) {
                // Nothing to do
            }
        }
    }
}
