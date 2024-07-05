package com.wpf.app.quickdialog

import android.app.Dialog
import com.wpf.app.quickutil.helper.getRealActivity
import com.wpf.app.quickutil.helper.generic.nullDefault

fun Dialog?.showInManager(
    showWithOther: Boolean = false,
    priority: Int = DialogManager.AUTO_PRIORITY,
    recoverInDismiss: Boolean = true
) {
    DialogManager.show(this, showWithOther, priority, recoverInDismiss)
}

object DialogManager {
    private val showingDialog = mutableMapOf<Int, MutableList<PriorityDialog>>()

    internal const val AUTO_PRIORITY = -1

    fun show(
        dialog: Dialog?,
        showWithOther: Boolean = false,
        priority: Int = AUTO_PRIORITY,
        recoverInDismiss: Boolean = true
    ) {
        if (dialog == null || dialog.getRealActivity()?.isFinishing == true || dialog.getRealActivity()?.isDestroyed == true) return
        val maxPriority = showingDialog.keys.maxOrNull().nullDefault(0)
        val curIsMax: Boolean
        val realPriority = if (priority == AUTO_PRIORITY) {
            curIsMax = true
            maxPriority + 1
        } else {
            curIsMax = maxPriority <= priority
            priority
        }
        val priorityDialogInMap = showingDialog[realPriority]
        val dialogDismissListener = if (dialog is QuickBaseDialog) {
            dialog.listener
        } else null
        val priorityDialog = PriorityDialog(
            dialog, realPriority, dialogDismissListener, recoverInDismiss, showWithOther
        )
        if (priorityDialogInMap == null) {
            showingDialog[realPriority] = mutableListOf(priorityDialog)
        } else {
            priorityDialogInMap.add(priorityDialog)
        }
        showingDialog.toSortedMap()
        if (curIsMax) {
            showingDialog.values.flatten().filter {
                it.isShowing() && !it.showWithOther
            }.forEach {
                it.isDismissByManager = true
                it.dismiss()
            }
            dialog.setOnDismissListener { dialogI ->
                dialogDismissListener?.onDismiss(dialogI)
                val allDialogs = showingDialog.values.flatten()
                val curPriorityDialog = allDialogs.findLast {
                    it.dialog == dialogI && !it.isDismissByManager
                }
                curPriorityDialog?.let {
                    showingDialog[curPriorityDialog.priority]?.remove(curPriorityDialog)
                }
                allDialogs.findLast {
                    it.recoverInDismiss && it.isDismissByShowing && allDialogs.last().dialog == dialogI
                }?.show()
            }
            dialog.show()
        } else {
            if (showWithOther) {
                dialog.show()
            }
        }
    }
}