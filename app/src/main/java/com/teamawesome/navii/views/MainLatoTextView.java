package com.teamawesome.navii.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.teamawesome.navii.R;

import java.util.Arrays;

/**
 * Created by williamkim on 16-06-04.
 */
public class MainLatoTextView extends TextView implements LatoType {

    private static final String BASE_FONT_NAME = "Lato-";

    public MainLatoTextView(Context context) {
        super(context);
        init(null);
    }

    public MainLatoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MainLatoTextView(Context context, AttributeSet attrs, int defStyleAttrs) {
        super(context, attrs, defStyleAttrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MainLatoTextView);
            String fontType = typedArray.getString(R.styleable.MainLatoTextView_fontType);
            setFont(fontType);
            typedArray.recycle();
        }
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
