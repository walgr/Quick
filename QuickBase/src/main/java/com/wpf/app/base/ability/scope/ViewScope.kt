package com.wpf.app.base.ability.scope

import android.content.Context
import android.view.View
import android.view.ViewGroup
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

interface ViewScope<T: View>: ContextScope {
    val view: T
    override val context: Context
        get() = view.context

    fun viewApply(builder: T.() -> Unit) {
        builder.invoke(view)
    }
}

fun <T : ViewGroup, R> T.withViewScope(block: ViewScope<T>.() -> R) : R {
    return block(createViewScope(this))
}

@OptIn(ExperimentalContracts::class)
inline fun <T: View> ViewScope<T>.viewApply(block: T.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block(this.view)
}

fun <T: View> createViewScope(view: T) = object : ViewScope<T> {
    override val view: T = view
}
