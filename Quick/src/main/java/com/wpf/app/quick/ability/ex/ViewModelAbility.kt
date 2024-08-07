package com.wpf.app.quick.ability.ex

import android.app.Activity
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.wpf.app.quickutil.Quick
import com.wpf.app.quickutil.ability.base.QuickAbility
import com.wpf.app.quick.ability.ex.base.QuickVMAbility
import com.wpf.app.quick.activity.viewmodel.QuickViewModel
import com.wpf.app.quickutil.helper.generic.asTo
import com.wpf.app.quickutil.helper.generic.forceTo
import com.wpf.app.quickutil.bind.QuickBindWrap

inline fun <reified VM : QuickViewModel<out Quick>> viewModel(
    noinline vmBuilder: (VM.() -> Unit)? = null
): MutableList<QuickAbility> = mutableListOf(object : QuickVMAbility<VM> {
    private var viewModel: VM? = null
    override fun getViewModel() = viewModel

    override fun afterGenerateContentView(owner: Quick, view: View) {
        super.afterGenerateContentView(owner, view)
        val viewModelCls = VM::class.java
        val activity = owner.forceTo<Activity>()
        viewModel = ViewModelProvider(
            owner.forceTo<ViewModelStoreOwner>(), ViewModelProvider.AndroidViewModelFactory(activity.application)
        )[viewModelCls]
        vmBuilder?.invoke(viewModel!!)
        QuickBindWrap.bind(activity, viewModel)
        viewModel.asTo<QuickViewModel<Quick>>()?.onViewCreated(activity.forceTo())
    }
})