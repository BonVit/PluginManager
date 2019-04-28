package com.vitaliibonar.pluginbeep

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*
import android.media.ToneGenerator
import android.media.AudioManager
import android.os.Handler
import android.os.Messenger

class PluginBeepService : Service() {
    val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)

    private val timerTask by lazy {
        object : TimerTask() {
            override fun run() {
                toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 200)
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