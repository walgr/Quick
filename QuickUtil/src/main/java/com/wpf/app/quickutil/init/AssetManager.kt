package com.wpf.app.quickutil.init

import android.content.Context
import android.content.res.AssetManager
import java.io.InputStream
import java.lang.ref.SoftReference

object AssetManager : ApplicationInitRegister {
    init {
        QuickInit.register(this)
    }

    override var context: SoftReference<Context>? = null

    fun open(name: String, accessMode: Int = AssetManager.ACCESS_STREAMING): InputStream? {
        return context?.get()?.assets?.open(name, accessMode)
    }
}