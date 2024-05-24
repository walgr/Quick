package com.wpf.app.base.ability.helper

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Space
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.base.ability.scope.viewGroupApply

fun Any.addView(child: View, layoutParams: LayoutParams? = null) {
    if (this is ViewGroupScope<out ViewGroup>) {
        addView(child, layoutParams)
    } else if (this is ViewGroup) {
        if (layoutParams == null) {
            addView(child)
        } else {
            addView(child, layoutParams)
        }
    } else {
        layoutParams?.let {
            child.layoutParams = layoutParams
        }
    }
}

fun ViewGroupScope<out ViewGroup>.space(space: Int, isHeight: Boolean = true) {
    viewGroupApply {
        addView(
            Space(context),
            if (isHeight) LayoutParams.WRAP_CONTENT else space,
            if (isHeight) space else LayoutParams.WRAP_CONTENT
        )
    }
}