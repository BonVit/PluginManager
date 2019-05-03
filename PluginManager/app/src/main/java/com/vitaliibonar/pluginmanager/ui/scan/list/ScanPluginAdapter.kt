package com.vitaliibonar.pluginmanager.ui.scan.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitaliibonar.pluginmanager.core.model.Plugin

class ScanPluginAdapter : RecyclerView.Adapter<ScanPluginHolder>() {

    private var plugins = arrayListOf<ScannedPlugin>()

    var addListener: ((plugin: ScannedPlugin) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanPluginHolder {
        val scanPluginHolder = ScanPluginHolder.inflate(parent)
        scanPluginHolder.addListener = addListener
        return scanPluginHolder
    }

    override fun getItemCount(): Int = plugins.size

    override fun onBindViewHolder(holder: ScanPluginHolder, position: Int) {
        holder.bind(plugins[position])
    }

    fun updatePlugins(plugins: ArrayList<ScannedPlugin>) {
        this.plugins = plugins
        notifyDataSetChanged()
    }

    fun updatePlugin(plugin: Plugin, isAdded: Boolean) {
        val index = plugins.indexOfFirst { it.plugin == plugin }
        if (index >= 0) {
            plugins[index].isAdded = isAdded
            notifyItemChanged(index)
        }
    }
}