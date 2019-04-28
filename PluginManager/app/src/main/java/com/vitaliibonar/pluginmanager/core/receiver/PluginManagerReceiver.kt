package com.vitaliibonar.pluginmanager.core.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vitaliibonar.pluginmanager.core.ServiceLocator
import com.vitaliibonar.pluginmanager.core.model.Plugin

class PluginManagerReceiver : BroadcastReceiver() {

    private val pluginManager = ServiceLocator.pluginManager

    override fun onReceive(context: Context?, intent: Intent?) {
        val packageName = intent?.extras?.getString(PACKAGE_NAME) ?: return
        val className = intent.extras?.getString(CLASS_NAME) ?: return

        if (packageName.isNotEmpty() && className.isNotEmpty()) {
            pluginManager.addPlugin(Plugin(packageName, className))
        }
    }

    companion object {
        const val INTENT_ACTION = "com.vitaliibonar.pluginmanager.AddPlugin"

        private const val PACKAGE_NAME = "PACKAGE_NAME"
        private const val CLASS_NAME = "CLASS_NAME"
    }
}