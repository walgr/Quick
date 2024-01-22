package com.wpf.app.quick.helper

import android.view.View
import androidx.databinding.ViewDataBinding
import com.wpf.app.quick.activity.QuickActivity
import com.wpf.app.quick.activity.QuickFragment
import com.wpf.app.quick.activity.QuickVBActivity
import com.wpf.app.quick.activity.QuickVBFragment
import com.wpf.app.quick.activity.QuickVMActivity
import com.wpf.app.quick.activity.QuickVMFragment
import com.wpf.app.quick.activity.QuickView
import com.wpf.app.quick.activity.viewmodel.QuickVBModel
import com.wpf.app.quick.activity.viewmodel.QuickViewModel
import com.wpf.app.quickdialog.QuickDialog
import com.wpf.app.quickdialog.QuickDialogFragment

fun View.toFragment(
    titleName: String = "",
    onFragmentInit: ((view: View?) -> Unit)? = null
): QuickFragment {
    return object : QuickFragment(layoutView = this@toFragment, titleName = titleName) {
        override fun initView(view: View?) {
            onFragmentInit?.invoke(view)
        }
    }
}

inline fun <reified VM : QuickViewModel<out QuickView>> View.toVMFragment(
    titleName: String = "",
    noinline onFragmentInit: ((view: View?) -> Unit)? = null
): QuickVMFragment<VM> {
    return object : QuickVMFragment<VM>(layoutView = this@toVMFragment, titleName = titleName) {
        override fun initView(view: View?) {
            super.initView(view)
            onFragmentInit?.invoke(view)
        }
    }
}

inline fun <reified VM : QuickVBModel<VB>, reified VB : ViewDataBinding> View.toVBFragment(
    titleName: String = "",
    noinline onFragmentInit: ((view: View?) -> Unit)? = null
): QuickVBFragment<VM, VB> {
    return object : QuickVBFragment<VM, VB>(layoutView = this@toVBFragment, titleName = titleName) {
        override fun initView(view: View?) {
            super.initView(view)
            onFragmentInit?.invoke(view)
        }
    }
}

fun View.toActivity(
    titleName: String = "",
    onActivityInit: (() -> Unit)? = null
): QuickActivity {
    return object : QuickActivity(layoutView = this@toActivity, titleName = titleName) {
        override fun initView(view: View?) {
            onActivityInit?.invoke()
        }
    }
}

inline fun <reified VM : QuickViewModel<out QuickView>> View.toVMActivity(
    titleName: String = "",
    noinline onActivityInit: ((view: View?) -> Unit)? = null
): QuickVMActivity<VM> {
    return object : QuickVMActivity<VM>(layoutView = this@toVMActivity, titleName = titleName) {
        override fun initView(view: View?) {
            onActivityInit?.invoke(view)
        }
    }
}

inline fun <reified VM : QuickVBModel<VB>, reified VB : ViewDataBinding> View.toVBActivity(
    titleName: String = "",
    noinline onActivityInit: ((view: VB?) -> Unit)? = null
): QuickVBActivity<VM, VB> {
    return object : QuickVBActivity<VM, VB>(layoutView = this@toVBActivity, titleName = titleName) {
        override fun initView(view: VB?) {
            super.initView(view)
            onActivityInit?.invoke(view)
        }
    }
}

fun View.toDialog(onDialogInit: ((view: View?) -> Unit)? = null): QuickDialog {
    return object : QuickDialog(this.context, layoutView = this) {
        override fun initView(view: View?) {
            onDialogInit?.invoke(view)
        }
    }
}

fun View.toDialogFragment(onDialogInit: ((view: View?) -> Unit)? = null): QuickDialogFragment {
    return object : QuickDialogFragment(layoutView = this) {
        override fun initView(view: View?) {
            onDialogInit?.invoke(view)
        }
    }
}