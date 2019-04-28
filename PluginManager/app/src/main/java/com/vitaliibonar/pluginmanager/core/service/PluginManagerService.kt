package com.vitaliibonar.pluginmanager.core.service

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import com.vitaliibonar.pluginmanager.core.ServiceLocator
import com.vitaliibonar.pluginmanager.core.model.Plugin
import com.vitaliibonar.pluginmanager.core.plugin.PluginListener
import com.vitaliibonar.pluginmanager.core.receiver.PluginManagerReceiver
import com.vitaliibonar.pluginmanager.core.utils.startForegroundNotification
import com.vitaliibonar.pluginmanager.core.utils.stopForegroundNotification

class PluginManagerService : Service(), ConnectionListener, PluginListener {

    private val connectionManager: ConnectionManager by lazy {
        val connectionManager = ConnectionManagerImpl(this)
        connectionManager.connectionListener = this@PluginManagerService
        connectionManager
    }
    private val pluginManager = ServiceLocator.pluginManager
    private val pluginManagerReceiver = PluginManagerReceiver()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundNotification(
            FOREGROUND_NOTIFICATION_ID,
            FOREGROUND_NOTIFICATION_CHANNEL_ID,
            FOREGROUND_NOTIFICATION_CHANNEL_NAME
        )

        // Preset plugin
        pluginManager.addPlugin(
            Plugin(
                "com.vitaliibonar.plugintoast",
                "com.vitaliibonar.plugintoast.PluginToastService"
            )
        )
        pluginManager.addPlugin(Plugin("com.vitaliibonar.pluginbeep", "com.vitaliibonar.pluginbeep.PluginBeepService"))

        pluginManager.plugins.forEach {
            if (it.isRunning) {
                connectPlugin(it)
            }
        }

        pluginManager.addListener(this)

        registerReceiver(pluginManagerReceiver, IntentFilter(PluginManagerReceiver.INTENT_ACTION))

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        stopForegroundNotification()
        connectionManager.disconnectAll()
        pluginManager.removeListener(this)
        unregisterReceiver(pluginManagerReceiver)
        super.onDestroy()
    }

    override fun onConnected(plugin: Plugin) {
        pluginManager.updatePlugin(plugin)
    }

    override fun onDisconnected(plugin: Plugin) {
        pluginManager.updatePlugin(plugin)
    }

    override fun onPluginAdded(plugin: Plugin) {
        if (plugin.isRunning) {
            connectPlugin(plugin)
        } else {
            connectionManager.disconnect(plugin)
        }
    }

    override fun onPluginRemoved(plugin: Plugin) {
        connectionManager.disconnect(plugin)
    }

    override fun onPluginChanged(plugin: Plugin) {
        if (plugin.isRunning) {
            connectPlugin(plugin)
        } else {
            connectionManager.disconnect(plugin)
        }
    }

    private fun connectPlugin(plugin: Plugin) {
        if (!connectionManager.connect(plugin)) {
            plugin.error = true
            plugin.isRunning = false
            pluginManager.updatePlugin(plugin)
        }
    }

    companion object {
        private const val FOREGROUND_NOTIFICATION_CHANNEL_NAME = "Notifications"
        private const val FOREGROUND_NOTIFICATION_CHANNEL_ID =
            "com.vitaliibonar.pluginmanager.core.service.PluginManagerService"
        private const val FOREGROUND_NOTIFICATION_ID = 3445
    }

}