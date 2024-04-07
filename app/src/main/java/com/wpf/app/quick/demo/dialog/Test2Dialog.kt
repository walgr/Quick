package com.wpf.app.quick.demo.dialog

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.wpf.app.quickdialog.QuickBaseDialog
import com.wpf.app.quickutil.run.runOnContext

class Test2Dialog(
    context: Context
): QuickBaseDialog(
    context,
    layoutViewInContext = runOnContext {
        TextView(context).apply {
            text = "Test2Dialog"
        }
    }
) {
    override fun initDialogGravity(): Int {
        return Gravity.BOTTOM
    }

    override fun initDialogMinHeight(): Int {
        return (getScreenHeight() * 0.3f).toInt()
    }

    override fun initDialogMaxHeight(): Int {
        return (getScreenHeight() * 0.9f).toInt()
    }

    override fun initDialogAdaptiveHeight(): Boolean {
        return true
    }
}