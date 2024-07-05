package com.wpf.app.quickutil.ability.base

import android.content.Context
import android.view.View

interface QuickInflateViewAbility: QuickInitViewAbility, Unique {
    override fun getPrimeKey() = "inflateView"
    fun layoutId(): Int = 0
    fun layoutView(): View? = null
    fun layoutViewCreate(): (Context.() -> View)? = null
}