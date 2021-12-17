package com.pichurchyk.motivationapp.data.notificationsUtils

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.pichurchyk.motivationapp.R
import com.pichurchyk.motivationapp.ui.main.MainActivity

class AlarmNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notification =
            NotificationCompat.Builder(context!!, "0")
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle("Get new motivation dose!")
                .setContentText("New motivation quote is available now!")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel =
            NotificationChannel(
                "0",
                "QuoteChannel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
        notificationChannel.description = "Quote notifications channel"
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.vibrationPattern =
            longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 100)
        notificationChannel.setShowBadge(false)

        notificationManager.createNotificationChannel(notificationChannel)

        val notificationIntent =
            Intent(context, MainActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(notificationIntent)
        val pendingIntent =
            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_NO_CREATE)
        notification.setContentIntent(pendingIntent)
        notificationManager.notify(0, notification.build())
    }
}