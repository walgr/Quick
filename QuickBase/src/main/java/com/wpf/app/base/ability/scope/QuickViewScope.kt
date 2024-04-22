package com.wpf.app.base.ability.scope

import com.wpf.app.base.QuickView

interface QuickViewScope<T: QuickView> {
    val self: T

    fun withSelf(builder: T.() -> Unit) {
        builder.invoke(self)
    }
}

fun <T: QuickView> createQuickViewScope(context: T) = object : QuickViewScope<T> {
    override val self: T = context
}