package com.wpf.app.quick.helper

import android.view.View
import androidx.databinding.ViewDataBinding
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.QuickFragment
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.ex.modelBinding
import com.wpf.app.quick.ability.ex.viewModel
import com.wpf.app.quick.ability.ex.with
import com.wpf.app.quick.activity.QuickView
import com.wpf.app.quick.activity.viewmodel.QuickVBModel
import com.wpf.app.quick.activity.viewmodel.QuickViewModel
import com.wpf.app.quickdialog.QuickDialog
import com.wpf.app.quickdialog.QuickDialogFragment

fun View.toFragment() = QuickFragment(
    contentView(layoutView = this@toFragment)
)

inline fun <reified VM : QuickViewModel<out QuickView>> View.toVMFragment(
) = QuickFragment(
    contentView(layoutView = this@toVMFragment).with(viewModel<VM>())
)


inline fun <reified VM : QuickVBModel<VB>, reified VB : ViewDataBinding> View.toVBFragment(
) = QuickFragment(
    contentView(layoutView = this@toVBFragment).with(modelBinding<VM, VB>())
)

fun View.toActivity(
    onActivityInit: ((view: View) -> Unit)? = null,
) = object : QuickActivity(
    contentView(layoutView = this)
) {
    override fun initView(view: View) {
        super.initView(view)
        onActivityInit?.invoke(view)
    }
}

inline fun <reified VM : QuickViewModel<out QuickView>> View.toVMActivity(
    noinline onActivityInit: ((view: View) -> Unit)? = null,
) = object : QuickActivity(
    contentView(layoutView = this).with(viewModel<VM>())
) {
    override fun initView(view: View) {
        super.initView(view)
        onActivityInit?.invoke(view)
    }
}

inline fun <reified VM : QuickVBModel<VB>, reified VB : ViewDataBinding> View.toVBActivity(
    noinline onActivityInit: ((view: VB) -> Unit)? = null,
) = QuickActivity(
    contentView(layoutView = this).with(modelBinding<VM, VB> {
        onActivityInit?.invoke(it)
    })
)

fun View.toDialog(onDialogInit: ((view: View?) -> Unit)? = null): QuickDialog {
    return object : QuickDialog(this.context, layoutView = this) {
        override fun initView(view: View?) {
            onDialogInit?.invoke(view)
        }
    }
}

fun View.toDialogFragment(): QuickDialogFragment {
    return QuickDialogFragment(layoutView = this)
}
