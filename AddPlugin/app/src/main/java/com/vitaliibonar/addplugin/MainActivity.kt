package com.vitaliibonar.addplugin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAdd.setOnClickListener {
            val packageName = etPackageName.text.toString()
            val className = etClassName.text.toString()
            if (packageName.isNotEmpty() && className.isNotEmpty()) {
                addPlugin(packageName, className)
                etPackageName.setText("")
                etClassName.setText("")
            }
        }
    }

    private fun addPlugin(packageName: String, className: String) {
        val intent = Intent()
        intent.putExtra("PACKAGE_NAME", packageName)
        intent.putExtra("CLASS_NAME", className)
        intent.action = "com.vitaliibonar.pluginmanager.AddPlugin"
        sendBroadcast(intent)
    }
}
