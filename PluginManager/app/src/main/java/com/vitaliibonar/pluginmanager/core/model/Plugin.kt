package com.vitaliibonar.pluginmanager.core.model

data class Plugin(var packageName: String, var className: String, var isRunning: Boolean = false, var error: Boolean = false) {
    override fun equals(other: Any?): Boolean {
        return if (other is Plugin) {
            packageName == other.packageName && className == other.className
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = packageName.hashCode()
        result = 31 * result + className.hashCode()
        return result
    }
}