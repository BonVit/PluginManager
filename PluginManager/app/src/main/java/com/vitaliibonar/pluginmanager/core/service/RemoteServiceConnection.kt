package com.vitaliibonar.pluginmanager.core.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import com.vitaliibonar.pluginmanager.core.model.Plugin

class RemoteServiceConnection(val plugin: Plugin) : ServiceConnection {

    var connectionListener: ConnectionListener? = null

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        plugin.isRunning = true
        plugin.error = false
        connectionListener?.onConnected(plugin)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        plugin.isRunning = false
        connectionListener?.onDisconnected(plugin)
    }

}

interface ConnectionListener {
    fun onConnected(plugin: Plugin)
    fun onDisconnected(plugin: Plugin)
}