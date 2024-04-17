package com.wpf.app.quick.ability.helper

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Space

fun ViewGroup.space(space: Int, isHeight: Boolean = true) {
    addView(
        Space(context),
        if (isHeight) LayoutParams.WRAP_CONTENT else space,
        if (isHeight) space else LayoutParams.WRAP_CONTENT
    )
}