package com.wpf.app.base.ability.base

import android.view.View
import com.wpf.app.quickutil.run.RunOnContext

interface QuickInflateViewAbility: QuickViewAbility, Unique {
    override fun getPrimeKey() = "inflateView"
    fun layoutId(): Int = 0
    fun layoutView(): View? = null
    fun layoutViewInContext(): RunOnContext<View>? = null
}