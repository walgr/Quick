package com.wpf.app.quickdialog

import android.app.Dialog
import android.content.DialogInterface.OnDismissListener

data class PriorityDialog(
    internal val dialog: Dialog,
    internal val priority: Int = DialogManager.AUTO_PRIORITY,
    internal val dismissListener: OnDismissListener? = null,
    //是否优先级高的弹窗消失后自我恢复
    internal val recoverInDismiss: Boolean = true,
    //是否不随其他弹窗影响
    internal val showWithOther: Boolean = false,
) {
    internal var isDismissByManager = false
    internal var isDismissByShowing = false
    fun isShowing() = dialog.isShowing

    fun show() {
        dialog.show()
        isDismissByShowing = false
        isDismissByManager = false
    }

    fun dismiss() {
        if (isShowing()) {
            dialog.dismiss()
            dismissListener?.onDismiss(dialog)
            isDismissByShowing = true
        }
    }
}