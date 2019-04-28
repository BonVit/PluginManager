package com.vitaliibonar.pluginmanager.core.service

import android.content.Context
import android.content.Intent
import com.vitaliibonar.pluginmanager.core.model.Plugin

class ConnectionManagerImpl(override val context: Context) : ConnectionManager, ConnectionListener {

    override var connectionListener: ConnectionListener? = null
    override val connections = arrayListOf<RemoteServiceConnection>()

    override fun connect(plugin: Plugin): Boolean {
        if (connections.find { it.plugin == plugin } != null) {
            return true
        }
        val connection = RemoteServiceConnection(plugin)
        connection.connectionListener = this
        val connectionIntent = Intent()
        connectionIntent.setClassName(plugin.packageName, plugin.className)
        try {
            if (context.bindService(connectionIntent, connection, Context.BIND_AUTO_CREATE)) {
                connections.add(connection)
                return true
            }
        } catch (ignored: Exception) {
        }
        return false
    }

    override fun disconnect(plugin: Plugin) {
        val connection =
            connections.find { it.plugin == plugin }
        if (connection != null) {
            connections.remove(connection)
            try {
                context.unbindService(connection)
            } catch (ignored: IllegalArgumentException) {
            }
        }
    }

    override fun disconnectAll() {
        connections.forEach { context.unbindService(it) }
    }

    override fun onConnected(plugin: Plugin) {
        connectionListener?.onConnected(plugin)
    }

    override fun onDisconnected(plugin: Plugin) {
        connections.removeAll { it.plugin == plugin }
        connectionListener?.onDisconnected(plugin)
    }
}