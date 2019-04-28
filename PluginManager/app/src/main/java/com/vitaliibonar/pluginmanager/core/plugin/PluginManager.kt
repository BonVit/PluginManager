package com.vitaliibonar.pluginmanager.core.plugin

import com.vitaliibonar.pluginmanager.core.model.Plugin

interface PluginManager {
    val plugins: List<Plugin>

    fun addPlugin(plugin: Plugin)
    fun removePlugin(plugin: Plugin)
    fun updatePlugin(plugin: Plugin)
    fun togglePlugin(plugin: Plugin)

    fun addListener(pluginListener: PluginListener)
    fun removeListener(pluginListener: PluginListener)
}

interface PluginListener {
    fun onPluginAdded(plugin: Plugin)
    fun onPluginRemoved(plugin: Plugin)
    fun onPluginChanged(plugin: Plugin)
}