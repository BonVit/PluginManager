package com.vitaliibonar.pluginmanager.ui.scan.list

import com.vitaliibonar.pluginmanager.core.model.Plugin

data class ScannedPlugin(val plugin: Plugin, var isAdded: Boolean = false)
