package com.wpf.app.quick.ability.helper

import android.content.res.ColorStateList
import androidx.annotation.ColorInt

fun createStateColor(
    @ColorInt defaultColor: Int,
    @ColorInt selectedColor: Int? = null,
    @ColorInt checkedColor: Int? = null,
    @ColorInt enabledColor: Int? = null,
): ColorStateList {
    return ColorStateList(
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
    )
}