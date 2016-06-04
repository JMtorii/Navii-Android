package com.teamawesome.navii.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.teamawesome.navii.R;
import com.teamawesome.navii.util.NaviiGraphics;

/**
 * Created by jtorii on 16-05-17.
 *
 * Credits to mallethugo
 */
public class ParallaxHorizontalScrollView extends HorizontalScrollView {

    public ParallaxHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    public ParallaxHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray attributesArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ParallaxHorizontalScrollView,
                0,
                0
        );

        try {
            setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            setVerticalScrollBarEnabled(false);
            setHorizontalScrollBarEnabled(false);

            Drawable background = attributesArray.getDrawable(R.styleable.ParallaxHorizontalScrollView_src);
            Bitmap bitmap = NaviiGraphics.drawableToBitmap(background);
            Bitmap grayscaleBitmap = NaviiGraphics.createGrayscale(bitmap);
            BitmapDrawable ob = new BitmapDrawable(getResources(), grayscaleBitmap);

            if (background != null) {
                // set background
                ImageView iv = new ImageView(context);
                iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                iv.setImageDrawable(background);
                iv.setImageDrawable(ob);
                iv.setAdjustViewBounds(true);
                this.addView(iv);
            }
        } finally {
            attributesArray.recycle();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Do not allow touch events.
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Do not allow touch events.
        return false;
    }


}
