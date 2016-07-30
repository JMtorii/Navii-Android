package com.teamawesome.navii.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.teamawesome.navii.R;

import java.util.Arrays;

/**
 * Created by JMtorii on 16-06-02.
 */
public class MainLatoButton extends Button implements LatoType {
    private static final String BASE_FONT_NAME = "Lato-";

    public MainLatoButton(Context context) {
        super(context);
        init(null);
    }

    public MainLatoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MainLatoButton(Context context, AttributeSet attrs, int defStyleAttrs) {
        super(context, attrs, defStyleAttrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MainLatoButton);
            String fontType = typedArray.getString(R.styleable.MainLatoButton_fontType);
            setFont(fontType);
            typedArray.recycle();
        }

//        setBackgroundResource(R.drawable.main_lato_button_bg);
    }

    public void setFont(String fontType) {
        String fontName = BASE_FONT_NAME;
        if (fontType == null) {
            fontName += defaultType;
        } else if (Arrays.asList(fontTypes).contains(fontType)) {
            fontName += fontType;
        } else {
            fontName += defaultType;
        }

        setTypeface(fontName);
    }

    public void setTypeface(String fontName) {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName + ".ttf");
        setTypeface(typeface);
    }
}
