package com.wpf.app.base.ability.scope

import android.app.Dialog
import android.view.View
import com.wpf.app.base.Quick
import com.wpf.app.quickutil.helper.contentView
import com.wpf.app.quickutil.other.forceTo

interface DialogScope<T: Dialog>: ViewScope<View>, QuickViewScope<Quick> {
    val dialog: T
    override val view: View
        get() = dialog.window?.contentView()!!
    override val self: Quick
        get() = dialog.forceTo()
}

fun <T> createDialogScope(dialog: T) where T: Dialog, T: Quick = object : DialogScope<T> {
    override val dialog: T
        get() = dialog

}