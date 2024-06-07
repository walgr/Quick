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

    fun viewApply(builder: T.() -> Unit) {
        builder.invoke(view)
    }
}

@Suppress("unused")
fun <T : View, R> T.withViewScope(block: ViewScope<T>.() -> R) : R {
    return block(this.createViewScope())
}

@Suppress("unused", "EXTENSION_SHADOWED_BY_MEMBER")
@OptIn(ExperimentalContracts::class)
inline fun <T: View> ViewScope<T>.viewApply(block: T.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block(this.view)
}

fun <T: View> T.createViewScope() = object : ViewScope<T> {
    override val view: T = this@createViewScope
}
