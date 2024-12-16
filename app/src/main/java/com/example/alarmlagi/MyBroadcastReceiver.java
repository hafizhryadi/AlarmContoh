package com.example.alarmlagi;

import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.widget.Button;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {
    String CHANNEL_ID = "default";
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Tes Notif")
                .setContentText("Isi Notif")
                // text panjang
                .setStyle(new NotificationCompat.BigTextStyle(). bigText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce mollis sollicitudin mi tincidunt bibendum. Fusce at ornare arcu, non vehicula justo. Morbi id dui leo. Sed in orci enim. Morbi consequat et metus a lacinia. Praesent porta iaculis porta. Donec dolor lorem, pellentesque at iaculis et, viverra sit amet eros. Quisque quis pellentesque tellus.\n" +
                        "\n" +
                        "Nunc at magna congue arcu porta congue. Proin sit amet luctus velit. Etiam sit amet leo eros. Donec felis eros, fermentum eu ultricies at, condimentum sed leo. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nulla at tempus erat. Quisque nisi tortor, tempus a tortor sed, semper dictum eros. In vulputate, augue ac cursus efficitur, leo lectus consequat est, at pellentesque dui felis vel nunc. Aliquam a pellentesque dui. Aenean auctor tincidunt metus ut interdum."))
                // set prioritas
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // custom sound
        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.kururing);
        Ringtone ringtone =  RingtoneManager.getRingtone(context, sound);
        ringtone.play();

        // kode yg harus digunakan pada android versi Oreo ke atas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Ini Alarm";
            String description = "Intinya Alarm";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, builder.build());


    }
}
