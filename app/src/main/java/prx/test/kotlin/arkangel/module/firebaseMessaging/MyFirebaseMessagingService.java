package prx.test.kotlin.arkangel.module.firebaseMessaging;

import com.google.firebase.messaging.RemoteMessage;
import com.hypertrack.lib.HyperTrackFirebaseMessagingService;

/**
 * Created by wessim23 on 3/23/18.
 */

public class MyFirebaseMessagingService extends HyperTrackFirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        /**
         * Call super.onMessageReceived() method
         * SDK uses this method to handle HyperTrack notifications
         * Refer to the https://dashboard.hypertrack.com/onboarding/fcm-android
         * for more info.
         */
        super.onMessageReceived(remoteMessage);
    }
}
