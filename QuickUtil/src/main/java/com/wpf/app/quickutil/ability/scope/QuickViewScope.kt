package com.wpf.app.quickutil.ability.scope

import com.wpf.app.quickutil.Quick

interface QuickViewScope<T: Quick> {
    val self: T

    @Suppress("unused")
    fun withSelf(builder: T.() -> Unit) {
        builder.invoke(self)
    }
}

fun <T: Quick> createQuickViewScope(context: T) = object : QuickViewScope<T> {
    override val self: T = context
}