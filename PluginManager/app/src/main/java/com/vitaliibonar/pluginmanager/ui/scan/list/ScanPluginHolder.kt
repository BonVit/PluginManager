package com.vitaliibonar.pluginmanager.ui.scan.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitaliibonar.pluginmanager.R
import kotlinx.android.synthetic.main.item_scan_plugin.view.*

class ScanPluginHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {

    private var plugin: ScannedPlugin? = null

    var addListener: ((plugin: ScannedPlugin) -> Unit)? = null

    init {
        with(itemView) {
            ivAdd.setOnClickListener {
                plugin?.let { addListener?.invoke(it) }
            }
        }
    }

    fun bind(plugin: ScannedPlugin) {
        this.plugin = plugin

        with(itemView) {
            tvPackageName.text = plugin.plugin.packageName
            tvClassName.text = plugin.plugin.className

            if (plugin.isAdded) {
                ivAdd.visibility = View.GONE
            } else {
                ivAdd.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        fun inflate(parent: ViewGroup?): ScanPluginHolder {
            return ScanPluginHolder(
                LayoutInflater.from(parent?.context).inflate(
                    R.layout.item_scan_plugin,
                    parent,
                    false
                )
            )
        }
    }
}