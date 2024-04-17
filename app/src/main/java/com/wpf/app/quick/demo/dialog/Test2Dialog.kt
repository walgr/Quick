package com.wpf.app.quick.demo.dialog

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.helper.background
import com.wpf.app.quick.ability.helper.gravity
import com.wpf.app.quick.ability.helper.myLayout
import com.wpf.app.quick.ability.helper.rect
import com.wpf.app.quickutil.helper.dpF
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.run.runOnContext
import com.wpf.app.quickwork.ability.helper.text
import com.wpf.app.quickwork.widget.QuickThemeDialog

class Test2Dialog(
    context: Context,
) : QuickThemeDialog(
    context,
    abilityList = contentView(layoutViewInContext = runOnContext {
        myLayout<LinearLayout>(layoutParams = matchLayoutParams()) {
            text(text = "Test2Dialog").gravity(Gravity.CENTER)
        }.background {
            rect(topLeftRadius = 12.dpF(), topRightRadius = 12.dpF())
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