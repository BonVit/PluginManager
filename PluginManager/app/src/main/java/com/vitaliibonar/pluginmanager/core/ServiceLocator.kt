package com.vitaliibonar.pluginmanager.core

import com.vitaliibonar.pluginmanager.core.plugin.PluginManagerImpl
import com.vitaliibonar.pluginmanager.core.plugin.PluginManager

object ServiceLocator {
    val pluginManager: PluginManager by lazy { PluginManagerImpl() }
}