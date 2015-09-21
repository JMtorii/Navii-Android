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

//    private Fragment mCurrentFragment;

    public NaviiFragmentManager(final FragmentManager fragmentManager, final int containerViewId) {
        mContainerViewId = containerViewId;
        mFragmentManager = fragmentManager;
    }

    public void switchFragment(final Fragment fragment, final int enterAnim, final int exitAnim,
                               final String tag, final boolean isReplace,
                               final boolean clearBackStack, final boolean isAddedToBackStack) {
        if (clearBackStack) {
            mFragmentManager.popBackStack();
        }

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (enterAnim != Constants.NO_ANIM && exitAnim != Constants.NO_ANIM) {
            ft.setCustomAnimations(enterAnim, exitAnim);
        }

        if (isReplace) {
            ft.replace(mContainerViewId, fragment, tag);
        } else {
            ft.add(mContainerViewId, fragment, tag);
        }

        if (isAddedToBackStack) {
            ft.addToBackStack(tag);
        }

        ft.commit();
//        mCurrentFragment = fragment;
    }


}
