package com.wpf.app.base.ability.scope

import android.content.Context
import android.view.View
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

interface ViewScope<T: View>: ContextScope {
    val view: T
    override val context: Context
        get() = view.context

    fun withView(builder: T.() -> Unit) {
        builder.invoke(view)
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T: View> ViewScope<T>.withView(block: T.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block(this.view)
}

fun <T: View> createViewScope(view: T) = object : ViewScope<T> {
    override val view: T = view
}
