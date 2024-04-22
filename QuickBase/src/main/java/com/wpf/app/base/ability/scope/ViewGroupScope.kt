package com.wpf.app.base.ability.scope

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

interface ViewGroupScope<T: ViewGroup>: ViewScope<T> {

    fun addView(child: View) {
        this.view.addView(child)
    }

    fun addView(child: View, layoutParams: LayoutParams) {
        this.view.addView(child, layoutParams)
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T: ViewGroup> ViewGroupScope<T>.withViewGroup(block: T.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block(this.view)
}


fun <T: ViewGroup> createViewGroupScope(viewGroup: T) = object : ViewGroupScope<T> {
    override val view: T = viewGroup
}