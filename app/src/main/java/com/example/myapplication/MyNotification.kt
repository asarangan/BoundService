package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.service.notification.NotificationListenerService
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import androidx.core.graphics.createBitmap

class MyNotification() {



    fun showNotification(context: Context){
        val CHANNEL_ID = "myChannelID"

        val notificationManager = NotificationManagerCompat.from(context)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("MyService")
            .setContentText("Service is Running")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_service_foreground)
            .build()

        val CHANNEL_NAME = "myChannelName"
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        channel.lightColor = Color.GREEN
        channel.enableLights(true)
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
            createNotificationChannel(channel)
        }


        Log.d(TAG, "Show Notification")
        notificationManager.notify(0, notification)
    }

}