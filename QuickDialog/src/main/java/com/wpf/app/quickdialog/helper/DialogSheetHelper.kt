package com.wpf.app.quickdialog.helper

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.wpf.app.quickdialog.QuickBaseBottomSheetDialog
import com.wpf.app.quickdialog.QuickBaseBottomSheetDialogFragment
import com.wpf.app.quickdialog.listeners.DialogSize
import com.wpf.app.quickutil.helper.match

/**
 * Created by 王朋飞 on 2022/6/21.
 */
object DialogSheetHelper {

    fun dealSheet(dialog: QuickBaseBottomSheetDialog): BottomSheetBehavior<View>? {
        val bottomSheet: View =
            dialog.window?.findViewById(com.google.android.material.R.id.design_bottom_sheet) ?: return null
        bottomSheet.layoutParams?.width = match
        bottomSheet.layoutParams?.height = match
        bottomSheet.background = ColorDrawable(Color.TRANSPARENT)
        val behavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomSheet)
        if (dialog.initPeekHeight() != DialogSize.NO_SET) {
            behavior.peekHeight = dialog.initPeekHeight()
        }
        if (dialog.initSheetState() != DialogSize.NO_SET) {
            behavior.state = dialog.initSheetState()
        }
        behavior.isHideable = dialog.hideAble()
        behavior.skipCollapsed = dialog.skipCollapsed()
        return behavior
    }

    fun dealSheet(dialog: QuickBaseBottomSheetDialogFragment): BottomSheetBehavior<View>? {
        val bottomSheet: View =
            dialog.getWindow()?.findViewById(com.google.android.material.R.id.design_bottom_sheet) ?: return null
        bottomSheet.layoutParams?.width = match
        bottomSheet.layoutParams?.height = match
        bottomSheet.background = ColorDrawable(Color.TRANSPARENT)
        val behavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomSheet)
        if (dialog.initPeekHeight() != DialogSize.NO_SET) {
            behavior.peekHeight = dialog.initPeekHeight()
        }
        if (dialog.initSheetState() != DialogSize.NO_SET) {
            behavior.state = dialog.initSheetState()
        }
        behavior.isHideable = dialog.hideAble()
        behavior.skipCollapsed = dialog.skipCollapsed()
        return behavior
    }
}