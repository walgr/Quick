package com.wpf.app.quickutil.init

import android.content.Context
import android.content.SharedPreferences
import com.wpf.app.quickutil.ApplicationInitRegister
import com.wpf.app.quickutil.QuickInit
import com.wpf.app.quickutil.bind.QuickBindWrap
import java.lang.ref.SoftReference

object SpManager: ApplicationInitRegister {
    private var context: SoftReference<Context>? = null
    private var spMap: MutableMap<String, SharedPreferences> = mutableMapOf()

    fun getSharedPreference(fileName: String = QuickBindWrap.getBindSpFileName()): SharedPreferences? {
        context?.get()?.let {
            spMap.getOrPut(fileName) {
                it.getSharedPreferences(fileName, Context.MODE_PRIVATE)
            }
        }
        return spMap[fileName]
    }

    override fun init(applicationContext: Context) {
        this.context = SoftReference(applicationContext)
    }

    override fun getContext(): Context? {
        return context?.get()
    }

    init {
        QuickInit.register(this)
    }
}