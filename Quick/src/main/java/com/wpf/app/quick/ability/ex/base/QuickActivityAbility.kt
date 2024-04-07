package com.wpf.app.quick.ability.ex.base

import android.view.View
import androidx.lifecycle.ViewModel
import com.wpf.app.base.ability.base.Unique
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickutil.run.RunOnContext

interface QuickInflateViewAbility : QuickLifecycleAbility, Unique {
    override fun getPrimeKey() = "inflateView"
    fun layoutId(): Int = 0
    fun layoutView(): View? = null
    fun layoutViewInContext(): RunOnContext<View>? = null
}

interface QuickVMAbility<VM : ViewModel> : QuickLifecycleAbility, BindViewModel<VM>, Unique {
    override fun getPrimeKey() = "viewModel"
}