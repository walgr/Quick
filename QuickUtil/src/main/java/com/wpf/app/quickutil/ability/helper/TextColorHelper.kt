package com.wpf.app.quickutil.ability.helper

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.wpf.app.quickutil.ability.scope.TextViewScope
import com.wpf.app.quickutil.ability.scope.createTextViewScope
import com.wpf.app.quickutil.helper.createStateColor

fun <T : TextView> T.textColor(
    @ColorRes colorRes: Int? = null,
    builder: (TextViewScope.() -> Unit)? = null,
): T {
    colorRes?.let {
        setTextColor(it)
    }
    builder?.invoke(createTextViewScope(this))
    return this@textColor
}

fun TextViewScope.state(
    @ColorInt defaultColor: Int,
    @ColorInt selectedColor: Int? = null,
    @ColorInt checkedColor: Int? = null,
    @ColorInt enabledColor: Int? = null,
): View {
    view.setTextColor(createStateColor(defaultColor, selectedColor, checkedColor, enabledColor))
    return view
}