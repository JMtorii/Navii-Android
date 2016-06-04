package com.teamawesome.navii.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.teamawesome.navii.R;

/**
 * Created by jtorii on 16-05-13.
 *
 * Credits to mallethugo
 */
public class ParallaxViewPager extends ViewPager {

    private ParallaxHorizontalScrollView mRealHorizontalScrollView;
    private float mParallaxVelocity;
    private int mRealHorizontalScrollViewWidth;

    public ParallaxViewPager(Context context) {
        super(context);
    }

    public ParallaxViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray attributesArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RealViewPager,
                0,
                0
        );

        try {
            mParallaxVelocity = attributesArray.getFloat(R.styleable.RealViewPager_parallaxVelocity, 0.3f);

        } finally {
            attributesArray.recycle();
        }
    }

    public void configure(ParallaxHorizontalScrollView realHorizontalScrollView) {
        this.mRealHorizontalScrollView = realHorizontalScrollView;
        this.mRealHorizontalScrollViewWidth = this.mRealHorizontalScrollView.getWidth();

        overrideScrollListener();
    }

//    public void configureWithMyListener(ParallaxHorizontalScrollView realHorizontalScrollView) {
//        this.mRealHorizontalScrollView = realHorizontalScrollView;
//        this.mRealHorizontalScrollViewWidth = this.mRealHorizontalScrollView.getWidth();
//    }

    public void setRealHorizontalScrollViewPosition(int scrollX, int position) {
        this.mRealHorizontalScrollView.scrollTo(Math.round((scrollX * mParallaxVelocity) + (position * mParallaxVelocity * this.mRealHorizontalScrollView.getWidth())), 0);
    }

    /**
     *  Override scroll listener
     *
     *  @param scrollX scroll X
     *  @param position current item position
     */
    public void manageScrollWithMyListeners(int scrollX, int position) {
        setRealHorizontalScrollViewPosition(scrollX, position);
    }

    private void overrideScrollListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.setOnScrollChangeListener(new OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    setRealHorizontalScrollViewPosition(scrollX, 0);
                }
            });
        } else {
            this.setOnPageChangeListener(new OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    setRealHorizontalScrollViewPosition(positionOffsetPixels, position);
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