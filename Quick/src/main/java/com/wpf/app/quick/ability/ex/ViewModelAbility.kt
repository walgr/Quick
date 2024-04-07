package com.wpf.app.quick.ability.ex

import android.app.Activity
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.wpf.app.base.ability.base.QuickViewAbility
import com.wpf.app.base.QuickView
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.quick.ability.ex.base.QuickVMAbility
import com.wpf.app.quick.activity.viewmodel.QuickViewModel
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo

inline fun <reified VM : QuickViewModel<out QuickView>> viewModel(
    noinline vmBuilder: (VM.() -> Unit)? = null
): MutableList<QuickAbility> = mutableListOf(object : QuickVMAbility<VM> {
    private var viewModel: VM? = null
    override fun getViewModel() = viewModel

    override fun afterGenerateContentView(owner: ViewModelStoreOwner, view: View) {
        super.afterGenerateContentView(owner, view)
        val viewModelCls = VM::class.java
        val activity = owner.forceTo<Activity>()
        viewModel = ViewModelProvider(
            owner, ViewModelProvider.AndroidViewModelFactory(activity.application)
        )[viewModelCls]
        vmBuilder?.invoke(viewModel!!)
        com.wpf.app.base.bind.QuickBindWrap.bind(activity, viewModel)
        viewModel.asTo<QuickViewModel<QuickView>>()?.onViewCreated(activity.forceTo())
    }
})