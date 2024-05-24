package com.wpf.app.base.ability.helper

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.wpf.app.base.ability.scope.ViewScope
import com.wpf.app.quickutil.helper.createStateColor

interface TextViewScope: ViewScope<TextView>

fun createTextViewScope(view: TextView) = object : TextViewScope {
    override val view: TextView = view
}

fun <T : TextView> T.textColor(
    @ColorRes colorRes: Int? = null,
    builder: (TextViewScope.() -> Unit)? = null,
): T {
    colorRes?.let {
        setTextColor(it)
    }
    builder?.invoke(createTextViewScope(this))
    return this
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