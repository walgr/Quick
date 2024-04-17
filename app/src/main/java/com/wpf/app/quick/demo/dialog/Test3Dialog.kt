package com.wpf.app.quick.demo.dialog

import android.content.Context
import android.view.Gravity
import com.wpf.app.quickdialog.QuickBaseDialog
import com.wpf.app.quickutil.run.runOnContext
import com.wpf.app.quickwork.ability.helper.text

class Test3Dialog(
    context: Context
): QuickBaseDialog(
    context,
    layoutViewInContext = runOnContext {
        text(text = "Test3Dialog")
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
        return false
    }
}