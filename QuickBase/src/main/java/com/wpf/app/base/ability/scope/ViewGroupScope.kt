package com.wpf.app.base.ability.scope

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

interface ViewGroupScope<T : ViewGroup> : ViewScope<T> {

    fun addView(child: View, layoutParams: LayoutParams? = null) {
        if (layoutParams == null) {
            this.view.addView(child)
        } else {
            this.view.addView(child, layoutParams)
        }
    }
}

fun <T : ViewGroup, R> T.withViewGroupScope(block: ViewGroupScope<T>.() -> R) : R {
    return block(this.createViewGroupScope())
}

@OptIn(ExperimentalContracts::class)
inline fun <T : ViewGroup> ViewGroupScope<T>.viewGroupApply(block: T.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block(this.view)
}

fun <T : ViewGroup> T.createViewGroupScope() = object : ViewGroupScope<T> {
    override val view: T = this@createViewGroupScope
}