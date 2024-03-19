package com.wpf.app.quick.ability.ex

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.wpf.app.quick.activity.QuickView
import com.wpf.app.quick.activity.viewmodel.QuickVBModel
import com.wpf.app.quick.helper.QuickDataBindingUtil
import com.wpf.app.quick.helper.getActivity
import com.wpf.app.quickrecyclerview.constant.BRConstant
import com.wpf.app.quickutil.bind.QuickBindWrap
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo

inline fun <reified VB : ViewDataBinding> binding(
    noinline vbBuilder: (VB.() -> Unit)? = null
): MutableList<QuickActivityAbility> = modelBinding<QuickVBModel<VB>, VB>(
    vbBuilder = vbBuilder
)

inline fun <reified VM : QuickVBModel<VB>, reified VB : ViewDataBinding> modelBinding(
    noinline vmBuilder: (VM.() -> Unit)? = null,
    noinline vbBuilder: (VB.() -> Unit)? = null,
    noinline mbBuilder: (VM.(vb: VB) -> Unit)? = null
): MutableList<QuickActivityAbility> = mutableListOf(object : QuickVMAbility<VM> {
    private var viewModel: VM? = null
    private var viewBinding: VB? = null

    override fun dealContentView(owner: ViewModelStoreOwner, view: View) {
        super.dealContentView(owner, view)

        val viewModelCls = VM::class.java
        val context = owner.forceTo<QuickView>()
        if (viewModelCls != QuickVBModel::class.java) {
            viewModel = ViewModelProvider(
                owner, ViewModelProvider.AndroidViewModelFactory(context.getActivity().application)
            )[viewModelCls]
            vmBuilder?.invoke(viewModel!!)
        }
        if (viewBinding == null && VB::class.java != ViewDataBinding::class.java) {
            viewBinding = DataBindingUtil.bind(view.findBinding()!!)
            viewBinding?.lifecycleOwner = context.forceTo()
        }
        viewBinding?.setVariable(BRConstant.viewModel, viewModel)
        viewBinding?.executePendingBindings()
        viewBinding?.let {
            viewModel?.mViewBinding = it
            vbBuilder?.invoke(it)
        }
        if (context is Activity) {
            QuickBindWrap.bind(context.forceTo<Activity>(), viewModel)
        } else if (context is Fragment) {
            QuickBindWrap.bind(context.forceTo<Fragment>(), viewModel)
        }
        viewModel.asTo<VM>()?.onBindingCreated(viewBinding!!)
        mbBuilder?.invoke(viewModel!!, viewBinding!!)
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