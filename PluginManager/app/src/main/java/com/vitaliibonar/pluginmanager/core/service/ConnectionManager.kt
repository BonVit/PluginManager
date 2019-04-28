package com.vitaliibonar.pluginmanager.core.service

import android.content.Context
import com.vitaliibonar.pluginmanager.core.model.Plugin

interface ConnectionManager {
    val context: Context
    val connections: List<RemoteServiceConnection>
    var connectionListener: ConnectionListener?

    fun connect(plugin: Plugin): Boolean
    fun disconnect(plugin: Plugin)
    fun disconnectAll()
}