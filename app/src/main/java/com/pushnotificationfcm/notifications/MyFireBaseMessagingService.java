package com.pushnotificationfcm.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pushnotificationfcm.R;
import com.pushnotificationfcm.helpers.Debug;

public class MyFireBaseMessagingService  extends FirebaseMessagingService {

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Debug.v("MyFirebaseMessagingService", "onMessageReceived:"+remoteMessage.getFrom());

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            // Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            Debug.v("MyFirebaseMessagingService", "onMessageReceived: NOTIFICATION TYPE"+remoteMessage.getNotification().getBody());
            Debug.v("MyFirebaseMessagingService", "onMessageReceived: NOTIFICATION TYPE"+remoteMessage.getNotification());

            // Debug.v("MyFirebaseMessagingService", "onMessageReceived: NOTIFICATION TYPE"+remoteMessage.getNotification().getTitle());

            handleNotification(remoteMessage.getNotification());
        }

        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Debug.v("MyFirebaseMessagingService1", "onMessageReceived: BODY TYPE"+ remoteMessage.getData());
//
//            try {
//                JSONObject json = new JSONObject(remoteMessage.getData());
//                handleDataMessage(json);
//            } catch (Exception e) {
//
//                Debug.v("MyFirebaseMessagingService1", "onMessageReceived:ERROR"+ e.getMessage());
//                // Log.e(TAG, "Exception: " + e.getMessage());
//            }
//        }
    }

    private void handleNotification(RemoteMessage.Notification message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            /*
            Intent pushNotification = new Intent("pushNotification");
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);*/
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel("101", "My Notifications", NotificationManager.IMPORTANCE_HIGH);
                // Configure the notification channel.
                notificationChannel.setDescription("Channel description");
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                notificationChannel.enableVibration(true);
                assert manager != null;
                manager.createNotificationChannel(notificationChannel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"101")
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setContentText(message.getBody());
            manager.notify(0, builder.build());
            // play notification sound
//              NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//              notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }
    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }
    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }


}
