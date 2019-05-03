package com.vitaliibonar.pluginmanager.ui.main.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitaliibonar.pluginmanager.R
import com.vitaliibonar.pluginmanager.core.model.Plugin
import kotlinx.android.synthetic.main.item_plugin.view.*

class PluginHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {

    private var plugin: Plugin? = null

    var removeListener: ((plugin: Plugin) -> Unit)? = null
    var actionListener: ((plugin: Plugin) -> Unit)? = null

    init {
        with(itemView) {
            ivRemove.setOnClickListener {
                plugin?.let { removeListener?.invoke(it) }
            }

            ivAction.setOnClickListener {
                plugin?.let { actionListener?.invoke(it) }
            }
        }
    }

    fun bind(plugin: Plugin) {
        this.plugin = plugin

        with(itemView) {
            if (plugin.isRunning) {
                ivAction.setImageResource(R.drawable.ic_pause)
            } else {
                ivAction.setImageResource(R.drawable.ic_play)
            }
            tvPackageName.text = plugin.packageName
            tvClassName.text = plugin.className
            if (plugin.error) {
                tvError.visibility = View.VISIBLE
            } else {
                tvError.visibility = View.GONE
            }
        }
    }

    companion object {
        fun inflate(parent: ViewGroup?): PluginHolder {
            return PluginHolder(
                LayoutInflater.from(parent?.context).inflate(
                    R.layout.item_plugin,
                    parent,
                    false
                )
            )
        }
    }
}