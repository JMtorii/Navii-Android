package com.teamawesome.navii.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.teamawesome.navii.R;

import java.util.Arrays;

/**
 * Created by williamkim on 16-06-04.
 */
public class MainLatoEditText extends EditText implements LatoType {

    private static final String BASE_FONT_NAME = "Lato-";

    public MainLatoEditText(Context context) {
        super(context);
        init(null);
    }

    public MainLatoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MainLatoEditText(Context context, AttributeSet attrs, int defStyleAttrs) {
        super(context, attrs, defStyleAttrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MainLatoEditText);
            String fontType = typedArray.getString(R.styleable.MainLatoEditText_fontType);
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

