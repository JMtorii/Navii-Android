package com.teamawesome.navii.util;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by JMtorii on 15-09-20.
 */
public class NaviiFragmentManager {

    /**
     * The activity's fragment manager
     */
    private final FragmentManager mFragmentManager;

    /**
     * The container view that contains the fragment frame layout
     */
    private final int mContainerViewId;

    /**
     * Used to switch fragments. Call this once per activity, per container view.
     * @param fragmentManager:  The activity's fragmentManager
     * @param containerViewId:  The view container for the fragment
     */
    public NaviiFragmentManager(FragmentManager fragmentManager, int containerViewId) {
        mContainerViewId = containerViewId;
        mFragmentManager = fragmentManager;
    }

    /**
     * Performs the fragment switch.
     * @param newFragment           Instance of the new fragment
     * @param enterAnim             Animation when the fragment enters
     * @param exitAnim              Animation when the fragment exits
     * @param tag                   Identifier for the fragment
     * @param isReplace             Replace or add
     * @param clearBackStack        Clear the fragment manager back stack
     * @param isAddedToBackStack    Force add to fragment manager back stack
     */
    public void switchFragment(Fragment newFragment, int enterAnim, int exitAnim, String tag,
                               boolean isReplace, boolean clearBackStack,
                               boolean isAddedToBackStack) {
        if (clearBackStack) {
            mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.setCustomAnimations(enterAnim, exitAnim);

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
