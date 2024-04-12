package com.wpf.app.quick.ability.ex.base

import androidx.lifecycle.ViewModel
import com.wpf.app.base.ability.base.QuickLifecycleAbility
import com.wpf.app.base.ability.base.Unique
import com.wpf.app.quickbind.interfaces.BindViewModel

interface QuickVMAbility<VM : ViewModel> : QuickLifecycleAbility, BindViewModel<VM>, Unique {
    override fun getPrimeKey() = "viewModel"
}