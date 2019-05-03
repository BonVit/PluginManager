package com.vitaliibonar.pluginmanager.ui.scan

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.vitaliibonar.pluginmanager.R
import com.vitaliibonar.pluginmanager.core.ServiceLocator
import com.vitaliibonar.pluginmanager.core.model.Plugin
import com.vitaliibonar.pluginmanager.core.plugin.PluginListener
import com.vitaliibonar.pluginmanager.ui.scan.list.ScanPluginAdapter
import com.vitaliibonar.pluginmanager.ui.scan.list.ScannedPlugin
import kotlinx.android.synthetic.main.activity_scan_plugins.*

class ScanPluginsActivity : AppCompatActivity(), PluginListener {

    private val scanPluginsAdapter = ScanPluginAdapter()
    private val pluginManager = ServiceLocator.pluginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_plugins)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pluginManager.addListener(this)

        initList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_scan_plugins, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            android.R.id.home -> onBackPressed()
            R.id.item_refresh -> scanPluginsAdapter.updatePlugins(ArrayList(scanPlugins()))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        pluginManager.removeListener(this)
    }

    override fun onPluginAdded(plugin: Plugin) {
        scanPluginsAdapter.updatePlugin(plugin, true)
    }

    override fun onPluginRemoved(plugin: Plugin) {
        scanPluginsAdapter.updatePlugin(plugin, false)
    }

    override fun onPluginChanged(plugin: Plugin) {}

    private fun initList() {
        scanPluginsAdapter.addListener = {
            pluginManager.addPlugin(it.plugin)
        }

        scanPluginsAdapter.updatePlugins(ArrayList(scanPlugins()))
        rvPlugins.adapter = scanPluginsAdapter
    }

    private fun scanPlugins(): List<ScannedPlugin> {
        val plugins = pluginManager.plugins
        val scannedPlugins = mutableListOf<ScannedPlugin>()
        val packageManager = packageManager
        val appInfos = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        for (appInfo in appInfos) {
            val metaData = appInfo.metaData
            val pluginComponentName = metaData?.getString("PLUGIN_COMPONENT_NAME", "") ?: continue
            if (pluginComponentName.isNotEmpty()) {
                val plugin = Plugin(appInfo.packageName, pluginComponentName)
                val scannedPlugin = ScannedPlugin(plugin, plugins.find { it == plugin } != null)
                scannedPlugins.add(scannedPlugin)
            }
        }
        return scannedPlugins
    }

}