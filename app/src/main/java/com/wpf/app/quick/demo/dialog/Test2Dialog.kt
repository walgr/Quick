package com.wpf.app.quick.demo.dialog

import android.content.Context
import android.view.Gravity
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quickutil.run.runOnContext
import com.wpf.app.quickwork.ability.text
import com.wpf.app.quickwork.widget.QuickThemeDialog

class Test2Dialog(
    context: Context,
) : QuickThemeDialog(
    context,
    abilityList = contentView(layoutViewInContext = runOnContext {
        text(text = "Test2Dialog")
    })
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