package com.wpf.app.quick.demo.dialog

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import com.wpf.app.base.ability.helper.background
import com.wpf.app.base.ability.helper.gravity
import com.wpf.app.base.ability.helper.rect
import com.wpf.app.base.ability.ex.contentView
import com.wpf.app.base.ability.helper.viewGroupCreate
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickwork.ability.helper.text
import com.wpf.app.quickwork.widget.theme.QuickThemeDialog

class Test2Dialog(
    context: Context,
) : QuickThemeDialog(
    context,
    abilityList = contentView(layoutViewCreate = {
            viewGroupCreate<LinearLayout>(layoutParams = matchMarginLayoutParams()) {
            text(text = "Test2Dialog").gravity(Gravity.CENTER)
        }.background {
            rect(topLeftRadius = 12f.dp, topRightRadius = 12f.dp)
        }
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
        return false
    }
}