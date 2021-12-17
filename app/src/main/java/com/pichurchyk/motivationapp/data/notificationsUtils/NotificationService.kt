package com.pichurchyk.motivationapp.data.notificationsUtils

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*

class NotificationService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        val intentExtras = intent?.extras
        startAlarm(intentExtras!!.getLong("time"))
        return null
    }

    override fun onCreate() {
        super.onCreate()
        startAlarm()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    private fun startAlarm(time: Long = System.currentTimeMillis()) {
        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val mIntent = Intent(this, AlarmNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, mIntent, 0)

        manager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pendingIntent)
    }
}