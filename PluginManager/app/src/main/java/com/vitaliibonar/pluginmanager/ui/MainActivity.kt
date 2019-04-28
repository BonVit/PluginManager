package com.vitaliibonar.pluginmanager.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vitaliibonar.pluginmanager.R
import com.vitaliibonar.pluginmanager.core.ServiceLocator
import com.vitaliibonar.pluginmanager.core.model.Plugin
import com.vitaliibonar.pluginmanager.core.plugin.PluginListener
import com.vitaliibonar.pluginmanager.core.service.PluginManagerService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PluginListener {

    private val pluginAdapter = PluginAdapter()
    private val pluginManager = ServiceLocator.pluginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startService(Intent(this, PluginManagerService::class.java))

        pluginManager.addListener(this)

        initList()

        buttonAdd.setOnClickListener {
            val packageName = etPackageName.text.toString()
            val className = etClassName.text.toString()
            if (packageName.isNotEmpty() && className.isNotEmpty()) {
                pluginManager.addPlugin(Plugin(packageName, className))
                etPackageName.setText("")
                etClassName.setText("")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pluginManager.removeListener(this)
    }

    override fun onPluginAdded(plugin: Plugin) {
        pluginAdapter.addPlugin(plugin)
    }

    override fun onPluginRemoved(plugin: Plugin) {
        pluginAdapter.removePlugin(plugin)
    }

    override fun onPluginChanged(plugin: Plugin) {
        pluginAdapter.updatePlugin(plugin)
    }

    private fun initList() {
        pluginAdapter.actionListener = {
            pluginManager.togglePlugin(it)
        }

        pluginAdapter.removeListener = {
            pluginManager.removePlugin(it)
        }

        pluginAdapter.updatePlugins(ArrayList(pluginManager.plugins))
        rvPlugins.adapter = pluginAdapter
    }

}
