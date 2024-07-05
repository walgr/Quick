package com.wpf.app.quick.ability.ex.base

import androidx.lifecycle.ViewModel
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickutil.ability.base.QuickLifecycleAbility
import com.wpf.app.quickutil.ability.base.Unique

interface QuickVMAbility<VM : ViewModel> : QuickLifecycleAbility, BindViewModel<VM>, Unique {
    override fun getPrimeKey() = "viewModel"
}