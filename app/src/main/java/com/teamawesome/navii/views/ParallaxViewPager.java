package com.teamawesome.navii.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.teamawesome.navii.R;

/**
 * Created by jtorii on 16-05-13.
 *
 * Credits to mallethugo
 */
public class ParallaxViewPager extends ViewPager {

    private ParallaxHorizontalScrollView mParallaxHorizontalScrollView;
    private float mParallaxVelocity;
    private int mParallaxHorizontalScrollViewWidth;

    public ParallaxViewPager(Context context) {
        super(context);
    }

    public ParallaxViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray attributesArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ParallaxViewPager,
                0,
                0
        );

        try {
            mParallaxVelocity = attributesArray.getFloat(R.styleable.ParallaxViewPager_parallaxVelocity, 0.3f);

        } finally {
            attributesArray.recycle();
        }
    }

    public void configure(ParallaxHorizontalScrollView parallaxHorizontalScrollView) {
        this.mParallaxHorizontalScrollView = parallaxHorizontalScrollView;
        this.mParallaxHorizontalScrollViewWidth = this.mParallaxHorizontalScrollView.getWidth();

        overrideScrollListener();
    }

    public void setParallaxHorizontalScrollViewPosition(int scrollX, int position) {
        this.mParallaxHorizontalScrollView.scrollTo(Math.round((scrollX * mParallaxVelocity) + (position * mParallaxVelocity * this.mParallaxHorizontalScrollView.getWidth())), 0);
    }

    private void overrideScrollListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.setOnScrollChangeListener(new OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    setParallaxHorizontalScrollViewPosition(scrollX, 0);
                }
            });
        } else {

            this.setOnPageChangeListener(new OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    setParallaxHorizontalScrollViewPosition(positionOffsetPixels, position);
                }

                @Override
                public void onPageSelected(int position) {
                    // nothing to do
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    // nothing to do
                }
            });
        }
    }
}