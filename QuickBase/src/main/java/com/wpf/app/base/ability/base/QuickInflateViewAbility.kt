package com.wpf.app.base.ability.base

import android.content.Context
import android.view.View

interface QuickInflateViewAbility: QuickViewAbility, Unique {
    override fun getPrimeKey() = "inflateView"
    fun layoutId(): Int = 0
    fun layoutView(): View? = null
    fun layoutViewCreate(): (Context.() -> View)? = null
}