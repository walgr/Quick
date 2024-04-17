package com.wpf.app.quickdialog.listeners

import android.app.Dialog
import androidx.annotation.CallSuper

/**
 * Created by 王朋飞 on 2022/6/21.
 */
interface DialogLifecycle {
    fun getLifecycleDialog(): Dialog
    var funcPrepare: (() -> Unit)?
    var funcShow: (Dialog.() -> Unit)?
    var funcDismiss: (Dialog.() -> Unit)?
    //dialog show之前执行
    @CallSuper
    fun onDialogPrepare() {
        funcPrepare?.invoke()
    }

    fun onDialogPrepare(func: () -> Unit) {
        this.funcPrepare = func
    }

    //dialog show之后执行
    @CallSuper
    fun onDialogShow() {
        funcShow?.invoke(getLifecycleDialog())
    }
    fun onDialogShow(func: Dialog.() -> Unit) {
        this.funcShow = func
    }

    //dialog dismiss后执行
    @CallSuper
    fun onDialogDismiss() {
        funcDismiss?.invoke(getLifecycleDialog())
    }
    fun onDialogDismiss(func: Dialog.() -> Unit) {
        this.funcDismiss = func
    }

}