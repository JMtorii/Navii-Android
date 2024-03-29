package com.teamawesome.navii.util;

import com.teamawesome.navii.R;

/**
 * Created by JMtorii on 16-06-19.
 */
public enum ToolbarConfiguration {
    SignUp(R.layout.activity_sign_up, R.string.sign_up_title, -1, 0, 1, R.drawable.ic_clear),
    ItineraryRecommend(R.layout.activity_itinerary_recommend, R.string.itinerary_recommend_title, 1, R.drawable.ic_chevron_left, -1, 0),
    PackageOverview(R.layout.activity_package_overview, R.string.package_overview_title, 1, R.drawable.ic_chevron_left, 0, R.string.package_overview_select),
    HeartAndSoul(R.layout.activity_itinerary_schedule, R.string.heart_and_soul_title, 1, R.drawable.ic_chevron_left, 0, R.string.heart_and_soul_select),
    HeartAndSoulDetails(R.layout.activity_heart_and_soul_details, R.string.heart_and_soul_details_title, 1, R.drawable.ic_chevron_left, -1, 0),
    HeartAndSoulSave(R.layout.activity_heart_and_soul_save, R.string.heart_and_soul_save_title, -1, 0, 1, R.drawable.ic_clear);

    public static final int BUTTON_TYPE_NONE = -1;
    public static final int BUTTON_TYPE_TEXT = 0;
    public static final int BUTTON_TYPE_IMAGE = 1;

    private int layoutResId;
    private int titleResId;
    private int leftButtonResType;
    private int leftResId;
    private int rightButtonResType;
    private int rightResId;

    ToolbarConfiguration(int layoutId, int titleId, int leftButtonType, int leftButtonId, int rightButtonType, int rightButtonId) {
        this.layoutResId = layoutId;
        this.titleResId = titleId;
        this.leftButtonResType = leftButtonType;
        this.leftResId = leftButtonId;
        this.rightButtonResType = rightButtonType;
        this.rightResId = rightButtonId;
    }

    public int getLayoutResId() {
        return layoutResId;
    }

    public void setLayoutResId(int layoutResId) {
        this.layoutResId = layoutResId;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public void setTitleResId(int titleResId) {
        this.titleResId = titleResId;
    }

    public int getLeftButtonResType() {
        return leftButtonResType;
    }

    public void setLeftButtonResType(int leftButtonResType) {
        this.leftButtonResType = leftButtonResType;
    }

    public int getLeftResId() {
        return leftResId;
    }

    public void setLeftResId(int leftResId) {
        this.leftResId = leftResId;
    }

    public int getRightButtonResType() {
        return rightButtonResType;
    }

    public void setRightButtonResType(int rightButtonResType) {
        this.rightButtonResType = rightButtonResType;
    }

    public int getRightResId() {
        return rightResId;
    }

    public void setRightResId(int rightResId) {
        this.rightResId = rightResId;
    }

    public boolean isLeftButtonImage() {
        return leftButtonResType == BUTTON_TYPE_IMAGE;
    }

    public boolean isRightButtonImage() {
        return rightButtonResType == BUTTON_TYPE_IMAGE;
    }
}
