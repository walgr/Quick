package com.wpf.app.quick.ability.ex

import android.app.Activity
import android.app.Dialog
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.wpf.app.base.Quick
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.QuickInitViewAbility
import com.wpf.app.base.bind.QuickBindWrap
import com.wpf.app.quick.ability.ex.base.QuickVMAbility
import com.wpf.app.quick.activity.viewmodel.QuickVBModel
import com.wpf.app.quick.helper.getActivity
import com.wpf.app.quickrecyclerview.constant.BRConstant
import com.wpf.app.quickrecyclerview.utils.findBinding
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo

inline fun <reified Self : Quick, reified VM : QuickVBModel<out Self, VB>, reified VB : ViewDataBinding> modelBindingWithSelf(
    noinline vmBuilder: (VM.(self: Self) -> Unit)? = null,
    noinline vbBuilder: (VB.(self: Self) -> Unit)? = null,
    noinline mbBuilder: (VB.(self: Self, vm: VM) -> Unit)? = null,
): MutableList<QuickAbility> = mutableListOf(object : QuickVMAbility<VM> {
    private var viewModel: VM? = null
    private var viewBinding: VB? = null

    override fun afterGenerateContentView(owner: Quick, view: View) {
        super.afterGenerateContentView(owner, view)
        val viewModelCls = VM::class.java
        val context = owner.forceTo<Quick>()
        if (viewModelCls != QuickVBModel::class.java && owner is ViewModelStoreOwner) {
            viewModel = ViewModelProvider(
                owner.forceTo<ViewModelStoreOwner>(), ViewModelProvider.AndroidViewModelFactory(context.getActivity().application)
            )[viewModelCls]
            vmBuilder?.invoke(viewModel!!, context.forceTo())
        }
        if (viewBinding == null && VB::class.java != ViewDataBinding::class.java) {
            viewBinding = DataBindingUtil.bind(view.findBinding(VB::class.java)!!)
            viewBinding?.lifecycleOwner = context.forceTo()
        }
        viewBinding?.setVariable(BRConstant.viewModel, viewModel)
        viewBinding?.executePendingBindings()
        viewBinding?.let {
            viewModel?.forceTo<QuickVBModel<Self, VB>>()?.quickView = context.forceTo()
            viewModel?.setViewBinding(it)
            vbBuilder?.invoke(it, context.forceTo())
        }
        when (context) {
            is Activity -> {
                QuickBindWrap.bind(context.forceTo<Activity>(), viewModel)
            }

            is Fragment -> {
                QuickBindWrap.bind(context.forceTo<Fragment>(), viewModel)
            }

            is Dialog -> {
                QuickBindWrap.bind(context.forceTo<Dialog>(), viewModel)
            }
        }
        viewModel?.asTo<VM>()?.onBindingCreated(viewBinding!!)
        if (viewBinding != null && viewModel != null) {
            mbBuilder?.invoke(viewBinding!!, context.forceTo(), viewModel!!)
        }
    }

    override fun getViewModel() = viewModel
})

inline fun <reified VM : QuickVBModel<out Quick, VB>, reified VB : ViewDataBinding> modelBinding(
    noinline vmBuilder: (VM.() -> Unit)? = null,
    noinline vbBuilder: (VB.() -> Unit)? = null,
    noinline mbBuilder: (VB.(vm: VM) -> Unit)? = null,
): MutableList<QuickAbility> {
    return modelBindingWithSelf<Quick, VM, VB>(
        { vmBuilder?.invoke(this) },
        { vbBuilder?.invoke(this) },
        { _, vm -> mbBuilder?.invoke(this, vm) })
}

inline fun <reified Self : Quick, reified VB : ViewDataBinding> bindingAndSelf(
    noinline vbBuilder: (VB.(self: Self) -> Unit)? = null,
): MutableList<QuickAbility> = mutableListOf(object : QuickInitViewAbility {
    override fun getPrimeKey(): String = "binding"
    private var viewBinding: VB? = null
    override fun afterGenerateContentView(owner: Quick, view: View) {
        super.afterGenerateContentView(owner, view)
        if (viewBinding == null && VB::class.java != ViewDataBinding::class.java) {
            viewBinding = DataBindingUtil.bind(view.findBinding(VB::class.java)!!)
        }
        viewBinding?.executePendingBindings()
        viewBinding?.let {
            vbBuilder?.invoke(it.forceTo(), owner.forceTo())
        }
    }
})

inline fun <reified VB : ViewDataBinding> binding(
    noinline vbBuilder: (VB.() -> Unit)? = null,
): MutableList<QuickAbility> = bindingAndSelf<Quick, VB>() {
    vbBuilder?.invoke(this)
}