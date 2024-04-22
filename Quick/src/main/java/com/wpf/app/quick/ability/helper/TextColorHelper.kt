package com.wpf.app.quick.ability.helper

import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.wpf.app.base.ability.scope.ViewScope

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
    view.setTextColor(ColorStateList(
        mutableListOf(intArrayOf()).apply {
            if (selectedColor != null) {
                add(intArrayOf(android.R.attr.state_selected))
            }
            if (checkedColor != null) {
                add(intArrayOf(android.R.attr.state_checkable))
            }
            if (enabledColor != null) {
                add(intArrayOf(android.R.attr.state_enabled))
            }
        }.toTypedArray(),
        mutableListOf(defaultColor).apply {
            if (selectedColor != null) {
                add(selectedColor)
            }
            if (checkedColor != null) {
                add(checkedColor)
            }
            if (enabledColor != null) {
                add(enabledColor)
            }
        }.toIntArray()
    ))
    return view
}