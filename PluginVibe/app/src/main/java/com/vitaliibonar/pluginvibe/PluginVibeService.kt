package com.vitaliibonar.pluginvibe

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import java.util.*

class PluginVibeService : Service() {
    val vibrator: Vibrator? by lazy { getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator }

    private val timerTask by lazy {
        object : TimerTask() {
            override fun run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    vibrator?.vibrate(1000)
                }
            }
        }
    }
    private val timer by lazy { Timer() }

    override fun onBind(intent: Intent?): IBinder? {
        timer.schedule(timerTask, 0, 10000)
        return Messenger(Handler()).binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        timer.cancel()
        stopSelf()
        return super.onUnbind(intent)
    }

}