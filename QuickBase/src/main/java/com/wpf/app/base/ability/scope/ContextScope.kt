package com.wpf.app.base.ability.scope

import android.content.Context

interface ContextScope: QuickScope {
    val context: Context

    fun withContext(builder: Context.() -> Unit) {
        builder.invoke(context)
    }
}

fun createContextScope(context: Context) = object : ContextScope {
    override val context: Context = context
}