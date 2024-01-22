package com.wpf.app.quickutil.init

import android.content.Context
import java.lang.ref.SoftReference

object QuickInit {
    private var application: SoftReference<Context>? = null
    private var initRegister = mutableListOf<ApplicationInitRegister>()
    fun init(applicationContext: Context) {
        if (application == null) {
            application = SoftReference(applicationContext)
        }
        initRegister.forEach {
            it.init(applicationContext)
        }
    }

    fun register(applicationInitRegister: ApplicationInitRegister) {
        initRegister.add(applicationInitRegister)
        if (applicationInitRegister.getContext() == null) {
            application?.get()?.let {
                applicationInitRegister.init(it)
            }
        }
    }
}

interface ApplicationInitRegister {
    fun init(applicationContext: Context)

    fun getContext(): Context?
}