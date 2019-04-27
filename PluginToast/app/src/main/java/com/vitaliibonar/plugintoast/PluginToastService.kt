package com.vitaliibonar.plugintoast

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Messenger
import android.widget.Toast
import java.util.*

class PluginToastService : Service() {
    private val mainHandler = Handler(Looper.getMainLooper())
    private val timerTask by lazy {
        object : TimerTask() {
            override fun run() {
                mainHandler.post {
                    count++
                    Toast.makeText(
                        this@PluginToastService,
                        "This is a message from toast plugin #$count",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    private val timer by lazy { Timer() }

    private var count = 0

    override fun onBind(intent: Intent?): IBinder? {
        count = 0
        timer.schedule(timerTask, 0, 10000)
        return Messenger(Handler()).binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        timer.cancel()
        count = 0
        return super.onUnbind(intent)
    }

}