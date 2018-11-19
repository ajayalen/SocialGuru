package com.socialnetwork.gcm;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

public class AppGcmListenerService extends GcmListenerService {


    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message", "");


      /*  PushEntitity pushEntitity = Utils.fromJson(message, PushEntitity.class);

        if (ApplicationController.isAppInForeground()) {
            EventBus.getDefault().post(pushEntitity);
        } else {
            showNotification(pushEntitity);
        }*/

    }
  /*  private void showNotification(PushEntitity pushData) {
        android.support.v4.app.NotificationCompat.Builder builder =
                new android.support.v4.app.NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.app_icon)
                        .setContentTitle(pushData.getMessage())
                        .setContentText("");
        Intent notificationIntent = new Intent(this, HomeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }*/

}
