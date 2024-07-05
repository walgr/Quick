package com.wpf.app.quick.demo.dialog

import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import com.wpf.app.quickutil.ability.ex.contentView
import com.wpf.app.quick.demo.R
import com.wpf.app.quickwork.ability.helper.text
import com.wpf.app.quickwork.widget.theme.QuickThemeDialog

class Test3Dialog(
    context: Context
): QuickThemeDialog(
    context,
    abilityList = contentView<FrameLayout> {
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

    override fun initDialogAnimStyle(): Int {
        return R.style.DialogBottomTopAnim
    }

    override fun initDialogAdaptiveHeight(): Boolean {
        return false
    }
}