package com.wpf.app.quick.helper

import android.view.View
import androidx.databinding.ViewDataBinding
import com.wpf.app.base.Quick
import com.wpf.app.base.ability.base.with
import com.wpf.app.base.ability.ex.contentView
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.QuickFragment
import com.wpf.app.quick.ability.ex.modelBindingWithSelf
import com.wpf.app.quick.ability.ex.viewModel
import com.wpf.app.quick.activity.viewmodel.QuickVBModel
import com.wpf.app.quick.activity.viewmodel.QuickViewModel
import com.wpf.app.quickdialog.QuickBaseDialog
import com.wpf.app.quickdialog.QuickBaseDialogFragment

fun View.toFragment() = QuickFragment(
    contentView(layoutView = this@toFragment)
)

@Suppress("unused")
inline fun <reified VM : QuickViewModel<out Quick>> View.toVMFragment(
) = QuickFragment(
    contentView(layoutView = this@toVMFragment).with(viewModel<VM>())
)


@Suppress("unused")
inline fun <reified VM : QuickVBModel<QuickFragment, VB>, reified VB : ViewDataBinding> View.toVBFragment(
) = QuickFragment(
    contentView(layoutView = this@toVBFragment).with(modelBindingWithSelf<QuickFragment, VM, VB>())
)

@Suppress("unused")
fun View.toActivity(
    onActivityInit: ((view: View) -> Unit)? = null,
) = object : QuickActivity(
    contentView(layoutView = this@toActivity)
) {
    override fun initView(view: View) {
        super.initView(view)
        onActivityInit?.invoke(view)
    }
}

@Suppress("unused")
inline fun <reified VM : QuickViewModel<out Quick>> View.toVMActivity(
    noinline onActivityInit: ((view: View) -> Unit)? = null,
) = object : QuickActivity(
    contentView(layoutView = this@toVMActivity).with(viewModel<VM>())
) {
    override fun initView(view: View) {
        super.initView(view)
        onActivityInit?.invoke(view)
    }
}

@Suppress("unused")
inline fun <reified VM : QuickVBModel<QuickActivity, VB>, reified VB : ViewDataBinding> View.toVBActivity(
    noinline onActivityInit: (VB.() -> Unit)? = null,
) = QuickActivity(
    contentView(layoutView = this@toVBActivity).with(modelBindingWithSelf<QuickActivity, VM, VB> { _, _ ->
        onActivityInit?.invoke(this)
    })
)

@Suppress("unused")
fun View.toDialog(onDialogInit: ((view: View?) -> Unit)? = null): QuickBaseDialog {
    return object : QuickBaseDialog(this.context, layoutView = this) {
        override fun initView(view: View) {
            onDialogInit?.invoke(view)
        }
    }
}

@Suppress("unused")
fun View.toDialogFragment(): QuickBaseDialogFragment {
    return QuickBaseDialogFragment(layoutView = this)
}
