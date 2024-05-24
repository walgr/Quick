package com.wpf.app.base.ability.scope

import com.wpf.app.base.Quick

interface QuickViewScope<T: Quick> {
    val self: T

    fun withSelf(builder: T.() -> Unit) {
        builder.invoke(self)
    }
}

fun <T: Quick> createQuickViewScope(context: T) = object : QuickViewScope<T> {
    override val self: T = context
}