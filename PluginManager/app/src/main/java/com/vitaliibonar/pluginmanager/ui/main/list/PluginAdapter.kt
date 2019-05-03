package com.vitaliibonar.pluginmanager.ui.main.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitaliibonar.pluginmanager.core.model.Plugin

class PluginAdapter : RecyclerView.Adapter<PluginHolder>() {

    private var plugins = arrayListOf<Plugin>()

    var removeListener: ((plugin: Plugin) -> Unit)? = null
    var actionListener: ((plugin: Plugin) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PluginHolder {
        val pluginHolder = PluginHolder.inflate(parent)
        pluginHolder.actionListener = actionListener
        pluginHolder.removeListener = removeListener
        return pluginHolder
    }

    override fun getItemCount(): Int = plugins.size

    override fun onBindViewHolder(holder: PluginHolder, position: Int) {
        holder.bind(plugins[position])
    }

    fun updatePlugins(plugins: ArrayList<Plugin>) {
        this.plugins = plugins
        notifyDataSetChanged()
    }

    fun removePlugin(plugin: Plugin) {
        val index = plugins.indexOf(plugin)
        if (index >= 0) {
            plugins.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun updatePlugin(plugin: Plugin) {
        val index = plugins.indexOf(plugin)
        if (index >= 0) {
            plugins[index] = plugin
            notifyItemChanged(index)
        }
    }

    fun addPlugin(plugin: Plugin) {
        val index = plugins.indexOf(plugin)
        if (index >= 0) {
            plugins[index] = plugin
            notifyItemChanged(index)
        } else {
            plugins.add(plugin)
            notifyItemInserted(plugins.size - 1)
        }
    }

}