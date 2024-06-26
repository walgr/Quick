package com.wpf.app.quickutil.init

import android.content.Context
import android.content.SharedPreferences
import java.lang.ref.SoftReference

object SpManager: ApplicationInitRegister {
    override var context: SoftReference<Context>? = null
    private var spMap: MutableMap<String, SharedPreferences> = mutableMapOf()

    fun getSharedPreference(fileName: String = "QuickViewSpBindFile"): SharedPreferences? {
        getContext()?.let {
            spMap.getOrPut(fileName) {
                it.getSharedPreferences(fileName, Context.MODE_PRIVATE)
            }
        }
        return spMap[fileName]
    }

    init {
        QuickInit.register(this)
    }
}