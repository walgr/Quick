package com.wpf.app.base.ability.helper

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import com.wpf.app.base.ability.scope.ViewGroupScope

fun Any.addView(child: View, layoutParams: LayoutParams) {
    if (this is ViewGroupScope<out ViewGroup>) {
        addView(child, layoutParams)
    } else if (this is ViewGroup) {
        addView(child, layoutParams)
    }
}