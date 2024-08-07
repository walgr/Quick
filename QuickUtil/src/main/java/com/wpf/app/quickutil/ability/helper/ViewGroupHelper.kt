package com.wpf.app.quickutil.ability.helper

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Space
import com.wpf.app.quickutil.ability.scope.ContextScope
import com.wpf.app.quickutil.ability.scope.ViewGroupScope
import com.wpf.app.quickutil.helper.layoutParams

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

fun ContextScope.space(space: Int, isHeight: Boolean = true): Space {
    val layoutParams = layoutParams<LayoutParams>(
        if (isHeight) LayoutParams.WRAP_CONTENT else space,
        if (isHeight) space else LayoutParams.WRAP_CONTENT
    )
    val spaceView = Space(context).apply {
        this.layoutParams = layoutParams
    }
    addView(spaceView, layoutParams)
    return spaceView
}