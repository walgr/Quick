package com.wpf.app.quickutil.init

import android.content.Context
import android.widget.Toast
import java.lang.ref.SoftReference

object ToastHelper : ApplicationInitRegister {
    override var context: SoftReference<Context>? = null
    fun show(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        getContext()?.let {
            Toast.makeText(it, msg, duration).show()
        }
    }

    init {
        QuickInit.register(this)
    }
}