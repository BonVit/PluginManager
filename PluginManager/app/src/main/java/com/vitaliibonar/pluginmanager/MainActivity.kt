package com.vitaliibonar.pluginmanager

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var isBound = false
    private val connection = RemoteServiceConnection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connectionIntent = Intent()
        connectionIntent.setClassName("com.vitaliibonar.plugintoast", "com.vitaliibonar.plugintoast.PluginToastService")
        bindService(connectionIntent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(connection)
        }
    }

    private inner class RemoteServiceConnection : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = true
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isBound = false
        }
    }

}
