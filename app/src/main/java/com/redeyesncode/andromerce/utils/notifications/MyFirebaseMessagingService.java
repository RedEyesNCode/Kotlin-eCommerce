package com.redeyesncode.andromerce.utils.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.redeyesncode.andromerce.R;
import com.redeyesncode.andromerce.utils.SplashActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService{

    private static final String TAG = "MyFirebaseMsgService";
    Context ct= this;
    public static boolean isNotificationClicked = false;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        String notificationBody = message.getNotification().getBody();
        String notificationKeyTitle = message.getNotification().getTitle();
        sendNotification(notificationBody,notificationKeyTitle,"HIGH");

    }
    private void sendNotification(String title, String messageBody,String type){

        try{
            Intent intent = null;
            intent = new Intent(this, SplashActivity.class)
                    .putExtra("NOTIFICATION",type);


                /*    Intent intent = new Intent().setClassName("packagename", "packagename.YourActivityname");
                // give any activity name which you want to open
                 */

            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
                currentapiVersion = R.mipmap.ic_launcher;
            } else{
                currentapiVersion = R.mipmap.ic_launcher;
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_IMMUTABLE);

            String CHANNEL_ID = "tonic_trades_channel_01";


            NotificationCompat.Builder mBuidler = new NotificationCompat.Builder(this);

            Notification notification = mBuidler.setSmallIcon(getNotificationIcon(mBuidler)).setTicker("TICKER_TEXT")
                    .setWhen(0)
                    .setAutoCancel(true)
                    .setSmallIcon(currentapiVersion)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),  R.mipmap.ic_launcher))
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setChannelId(CHANNEL_ID)
                    .setContentText(messageBody).build();

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            int time = (int) System.currentTimeMillis();

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                //the id of the Channel.

                CharSequence name = "Tonic Trades   ";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                notificationManager.createNotificationChannel(mChannel);
            }
            notificationManager.notify(time,notification);
        } catch (Exception e){
            e.printStackTrace();
        }


    }
    //Method to get Notification Icon.

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            int color = 0x008000;
            notificationBuilder.setColor(color);
            return  R.mipmap.ic_launcher;
        }
        return  R.mipmap.ic_launcher;
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
    }
}
