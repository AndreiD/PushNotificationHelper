package tutorialx.pushnotificationhelper.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import tutorialx.pushnotificationhelper.R;

/**
 * Service used for receiving GCM messages. When a vector_drawable_email is received this service will log it.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

  private static final String TAG = "MyFirebaseMsgService";

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);

    // Not getting messages here? See why this may be: https://firebase.google.com/support/faq/#fcm-android-background
    Log.d(TAG, "From: " + remoteMessage.getFrom());

    // Check if message contains a data payload.
    if (remoteMessage.getData().size() > 0) {
      Log.d(TAG, "Message data payload: " + remoteMessage.getData());

      //if (/* Check if data needs to be processed by long running job */ true) {
      //  // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
      //  scheduleJob();
      //} else {
      //  // Handle message within 10 seconds
      //  handleNow();
      //}

    }

    // Check if message contains a notification payload.
    if (remoteMessage.getNotification() != null) {
      Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
      sendNotification(remoteMessage.getNotification().getBody());
    }
  }

  @Override
  public void onDeletedMessages() {

    //sendNotification("Deleted messages on server");
  }

  @Override
  public void onMessageSent(String msgId) {
    //sendNotification("Upstream vector_drawable_email sent. Id=" + msgId);
  }

  private void sendNotification(String string) {
    Intent broadcastIntent = new Intent("NotificationReceived");
    this.sendBroadcast(broadcastIntent);
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)  //see https://stackoverflow.com/a/46946289
        .setSound(defaultSoundUri);
    notificationBuilder.setColor(ContextCompat.getColor(this, R.color.colorAccent));
    NotificationManager notificationManager =
        (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

    notificationBuilder.setSmallIcon(R.mipmap.ic_launcher_round)
        .setContentTitle(this.getResources().getString(R.string.app_name))
        .setContentText(string)
        .setStyle(new NotificationCompat.BigTextStyle()
            .bigText(string))
        .setPriority(Notification.PRIORITY_HIGH)
        .setAutoCancel(true);
    notificationManager.notify(1337, notificationBuilder.build());
  }
}