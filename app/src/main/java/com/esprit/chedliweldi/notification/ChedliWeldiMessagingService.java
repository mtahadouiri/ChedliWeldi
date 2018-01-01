package com.esprit.chedliweldi.notification;

/**
 * Created by oussama_2 on 11/27/2017.
 */

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;
import com.esprit.chedliweldi.Activities.MyOfferActivity;
import com.esprit.chedliweldi.Activities.RequestsActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import com.esprit.chedliweldi.Activities.LoginActivity;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.R;

public class ChedliWeldiMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebassgSeMervice";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("oussaam","message received");
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Chat containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "size: " + remoteMessage.getData().size());
      //  testNotif();
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


           sendNotification(remoteMessage.getData());



            //  sendNotification("sdfsdf");

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
            //    scheduleJob();


            } else {
                // Handle message within 10 seconds


            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
          //  sendNotification("hello","oussama","s");

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */


    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }


    private NotificationTarget notificationTarget;


    private void sendNotification(Map<String,String> data) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String fullName=data.get("fullName");
        String id=data.get("id");

        String photo =AppController.IMAGE_SERVER_ADRESS+ data.get("photo");
        String type = data.get("type");
        boolean custom=false;



        //intent///

        ///


        setupNotificationChannel();



        RemoteViews remoteViews=null;




        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "aaa");

        notificationBuilder.setAutoCancel(true)
               .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(1)
                .setContentTitle("Content Title")
                .setContentText("Content Text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher);
            //    .setContentIntent(resultPendingIntent)


        if(type!=null && type.equals("message")){


            Intent resultIntent = new Intent(this, LoginActivity.class);
            String content=data.get("content");
            remoteViews=setupRemoteViews(fullName==null ? "null":fullName,content==null ? "content":content);
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            notificationBuilder.setContent(remoteViews);
            notificationBuilder.setTicker(fullName +"send u a message");
            notificationBuilder.setContentIntent(resultPendingIntent);
            custom=true;

        }

        if(type!=null && type.equals("request")){
            String idRequest=data.get("id_request");
            String idOffer=data.get("id_offer");
            Intent resultIntent = new Intent(this,MyOfferActivity.class);
            resultIntent.putExtra("id_request",idRequest);
            resultIntent.putExtra("id_offer",idOffer);

            remoteViews=setupRemoteViews(fullName==null ? "null":fullName,"send you a request");
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            notificationBuilder.setContent(remoteViews);
            notificationBuilder.setTicker(fullName +"send u a request");
            notificationBuilder.setContentIntent(resultPendingIntent);
            custom=true;

        }



        Notification notification= notificationBuilder.build();
if(custom){

    notificationTarget = new NotificationTarget(
            getApplicationContext(),
            remoteViews,
            R.id.remoteview_notification_icon,
            notification,
            1);

    new Handler(Looper.getMainLooper()).post(new Runnable() {
        @Override
        public void run() {
            Glide
                    .with(getApplicationContext())
                    .load(photo)
                    .asBitmap()
                    .transform(new AppController.CircleTransform(getApplicationContext()))
                    .into(notificationTarget);

        }
    });

}


        notificationManager.notify(1,notification);

    }



    void setupNotificationChannel(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Hello";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel("aaa", name, importance);

            notificationManager.createNotificationChannel(mChannel);
        }
    }

    RemoteViews setupRemoteViews(String title ,String content){
        final RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.notification);
        remoteViews.setImageViewResource(R.id.remoteview_notification_icon, R.drawable.man);
        remoteViews.setTextViewText(R.id.remoteview_notification_headline, title);
        remoteViews.setTextViewText(R.id.remoteview_notification_short_message, content);
        return remoteViews;
    }

    void testNotif(){
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);



        final RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.notification);

        remoteViews.setImageViewResource(R.id.remoteview_notification_icon, R.drawable.man);

        remoteViews.setTextViewText(R.id.remoteview_notification_headline, "Headline");
        remoteViews.setTextViewText(R.id.remoteview_notification_short_message, "Short Message");

// build notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this,"aa")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Content Title")
                        .setContentText("Content Text")
                        .setContent(remoteViews)
                        .setPriority( 1);

        final Notification notification = mBuilder.build();

// set big content view for newer androids
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification.bigContentView = remoteViews;
        }


        mNotificationManager.notify(1, notification);

    }

}


