package com.vitaliibonar.pluginmanager.core.utils

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.vitaliibonar.pluginmanager.R
import com.vitaliibonar.pluginmanager.ui.MainActivity

fun Service.startForegroundNotification(notificationId: Int, channelId: String, channelName: String) {
    createNotificationChannel(channelId, channelName)

    val notificationIntent = Intent(this, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        this, 0,
        notificationIntent, 0
    )
    val notification = NotificationCompat.Builder(
        this,
        channelId
    )
        .setSmallIcon(R.mipmap.ic_launcher_round)
        .setContentIntent(pendingIntent)
        .build()

    startForeground(notificationId, notification)
}

fun Service.stopForegroundNotification() {
    stopForeground(true)
}