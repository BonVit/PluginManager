package com.vitaliibonar.pluginmanager.core.plugin

import com.vitaliibonar.pluginmanager.core.model.Plugin

class PluginManagerImpl : PluginManager {
    override val plugins = arrayListOf<Plugin>()
    private val pluginsListeners = arrayListOf<PluginListener>()

    override fun addPlugin(plugin: Plugin) {
        if (!plugins.contains(plugin)) {
            plugins.add(plugin)
            notifyPluginAdded(plugin)
        }
    }

    override fun removePlugin(plugin: Plugin) {
        if (plugins.remove(plugin)) {
            notifyPluginRemoved(plugin)
        }
    }

    override fun updatePlugin(plugin: Plugin) {
        val index = plugins.indexOf(plugin)
        if (index >= 0) {
            plugins[index] = plugin
            notifyPluginChanged(plugin)
        }
    }

    override fun togglePlugin(plugin: Plugin) {
        val index = plugins.indexOf(plugin)
        if (index >= 0) {
            plugins[index].isRunning = !plugins[index].isRunning
            notifyPluginChanged(plugin)
        }
    }

    override fun addListener(pluginListener: PluginListener) {
        pluginsListeners.add(pluginListener)
    }

    override fun removeListener(pluginListener: PluginListener) {
        pluginsListeners.remove(pluginListener)
    }

    private fun notifyPluginAdded(plugin: Plugin) {
        pluginsListeners.forEach {
            it.onPluginAdded(plugin)
        }
    }

    private fun notifyPluginRemoved(plugin: Plugin) {
        pluginsListeners.forEach {
            it.onPluginRemoved(plugin)
        }
    }

    private fun notifyPluginChanged(plugin: Plugin) {
        pluginsListeners.forEach {
            it.onPluginChanged(plugin)
        }
    }
}