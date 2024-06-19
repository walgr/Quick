package com.wpf.app.base.ability.scope

import android.content.Context

interface ContextScope: QuickScope {
    val context: Context

    fun withContext(builder: Context.() -> Unit) {
        builder.invoke(context)
    }
}

fun Context.createContextScope() = object : ContextScope {
    override val context: Context = this@createContextScope
}