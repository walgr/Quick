package com.wpf.app.quick.ability.ex

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.wpf.app.base.QuickView
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.bind.QuickBindWrap
import com.wpf.app.quick.ability.ex.base.QuickVMAbility
import com.wpf.app.quick.activity.viewmodel.QuickVBModel
import com.wpf.app.quick.helper.QuickDataBindingUtil
import com.wpf.app.quick.helper.getActivity
import com.wpf.app.quickrecyclerview.constant.BRConstant
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo

inline fun <reified Self : QuickView, reified VM : QuickVBModel<out Self, VB>, reified VB : ViewDataBinding> modelBindingWithSelf(
    noinline vmBuilder: (VM.(self: Self) -> Unit)? = null,
    noinline vbBuilder: (VB.(self: Self) -> Unit)? = null,
    noinline mbBuilder: (VB.(self: Self, vm: VM) -> Unit)? = null,
): MutableList<QuickAbility> = mutableListOf(object : QuickVMAbility<VM> {
    private var viewModel: VM? = null
    private var viewBinding: VB? = null

    override fun afterGenerateContentView(owner: ViewModelStoreOwner, view: View) {
        super.afterGenerateContentView(owner, view)
        val viewModelCls = VM::class.java
        val context = owner.forceTo<QuickView>()
        if (viewModelCls != QuickVBModel::class.java) {
            viewModel = ViewModelProvider(
                owner, ViewModelProvider.AndroidViewModelFactory(context.getActivity().application)
            )[viewModelCls]
            vmBuilder?.invoke(viewModel!!, context.forceTo())
        }
        if (viewBinding == null && VB::class.java != ViewDataBinding::class.java) {
            viewBinding = DataBindingUtil.bind(view.findBinding()!!)
            viewBinding?.lifecycleOwner = context.forceTo()
        }
        viewBinding?.setVariable(BRConstant.viewModel, viewModel)
        viewBinding?.executePendingBindings()
        viewBinding?.let {
            viewModel?.forceTo<QuickVBModel<Self, VB>>()?.quickView = context.forceTo()
            viewModel?.setViewBinding(it)
            vbBuilder?.invoke(it, context.forceTo())
        }
        if (context is Activity) {
            QuickBindWrap.bind(context.forceTo<Activity>(), viewModel)
        } else if (context is Fragment) {
            QuickBindWrap.bind(context.forceTo<Fragment>(), viewModel)
        }
        viewModel.asTo<VM>()?.onBindingCreated(viewBinding!!)
        if (viewBinding != null && viewModel != null) {
            mbBuilder?.invoke(viewBinding!!, context.forceTo(), viewModel!!)
        }
    }

    private fun View.findBinding(): View? {
        if (QuickDataBindingUtil.bind<ViewDataBinding>(this) != null) return this
        if (this is ViewGroup) {
            for (it in 0 until this.childCount) {
                val child = getChildAt(it)
                if (QuickDataBindingUtil.bind<ViewDataBinding>(child) != null) return child
            }
            for (it in 0 until this.childCount) {
                return getChildAt(it).findBinding()
            }
        }
        return null
    }

    override fun getViewModel() = viewModel
})

inline fun <reified VM : QuickVBModel<out QuickView, VB>, reified VB : ViewDataBinding> modelBinding(
    noinline vmBuilder: (VM.() -> Unit)? = null,
    noinline vbBuilder: (VB.() -> Unit)? = null,
    noinline mbBuilder: (VB.(vm: VM) -> Unit)? = null,
): MutableList<QuickAbility> {
    return modelBindingWithSelf<QuickView, VM, VB>({ vmBuilder?.invoke(this) },
        { vbBuilder?.invoke(this) },
        { _, vm -> mbBuilder?.invoke(this, vm) })
}

inline fun <reified Self : QuickView, reified VB : ViewDataBinding> bindingAndSelf(
    noinline vbBuilder: (VB.(self: Self) -> Unit)? = null,
): MutableList<QuickAbility> =
    modelBindingWithSelf<Self, QuickVBModel<Self, VB>, VB>(vbBuilder = {
        vbBuilder?.invoke(this, it)
    })

inline fun <reified VB : ViewDataBinding> binding(
    noinline vbBuilder: (VB.() -> Unit)? = null,
): MutableList<QuickAbility> = modelBinding<QuickVBModel<QuickView, VB>, VB>(
    vbBuilder = vbBuilder
)