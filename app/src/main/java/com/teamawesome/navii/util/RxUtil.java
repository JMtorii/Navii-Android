package com.teamawesome.navii.util;

import rx.Subscription;

/**
 * Created by JMtorii on 16-06-11.
 */
public class RxUtil {
    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
