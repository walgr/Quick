package com.wpf.app.quick.ability.ex

import android.app.Activity
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.wpf.app.quick.ability.QuickActivityAbility
import com.wpf.app.quick.ability.QuickVMAbility
import com.wpf.app.quick.activity.QuickView
import com.wpf.app.quick.activity.viewmodel.QuickViewModel
import com.wpf.app.quickutil.bind.QuickBindWrap
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo

inline fun <reified VM : QuickViewModel<out QuickView>> viewModel(
    noinline vmBuilder: (VM.() -> Unit)? = null
): MutableList<QuickActivityAbility> = mutableListOf(object : QuickVMAbility<VM> {
    private var viewModel: VM? = null
    override fun getViewModel() = viewModel

    override fun dealContentView(owner: ViewModelStoreOwner, view: View) {
        super.dealContentView(owner, view)
        val viewModelCls = VM::class.java
        val activity = owner.forceTo<Activity>()
        viewModel = ViewModelProvider(
            owner, ViewModelProvider.AndroidViewModelFactory(activity.application)
        )[viewModelCls]
        vmBuilder?.invoke(viewModel!!)
        QuickBindWrap.bind(activity, viewModel)
        viewModel.asTo<QuickViewModel<QuickView>>()?.onViewCreated(activity.forceTo())
    }
})