package com.teamawesome.navii.util;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by JMtorii on 15-09-20.
 */
public class NaviiFragmentManager {
    private final FragmentManager mFragmentManager;
    private final int mContainerViewId;

    public NaviiFragmentManager(FragmentManager fragmentManager, int containerViewId) {
        mContainerViewId = containerViewId;
        mFragmentManager = fragmentManager;
    }

    public void switchFragment(Fragment newFragment, int enterAnim, int exitAnim, String tag,
                               boolean isReplace, boolean clearBackStack,
                               boolean isAddedToBackStack) {
        if (clearBackStack) {
            mFragmentManager.popBackStack();
        }

        FragmentTransaction ft = mFragmentManager.beginTransaction();

        // check if animations are valid
        if (enterAnim == 0 || enterAnim < -1 ) {
            enterAnim = Constants.NO_ANIM;
        }

        if (exitAnim == 0 || exitAnim < -1) {
            exitAnim = Constants.NO_ANIM;
        }

        if (enterAnim != Constants.NO_ANIM && exitAnim != Constants.NO_ANIM) {
            ft.setCustomAnimations(enterAnim, exitAnim);
        }

        if (isReplace) {
            ft.replace(mContainerViewId, newFragment, tag);
        } else {
            ft.add(mContainerViewId, newFragment, tag);
        }

        if (isAddedToBackStack) {
            ft.addToBackStack(tag);
        }

        ft.commit();
    }
}
