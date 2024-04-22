package com.wpf.app.quick.ability.helper

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Space
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.base.ability.scope.withViewGroup

fun ViewGroupScope<out ViewGroup>.space(space: Int, isHeight: Boolean = true) {
    withViewGroup {
        addView(
            Space(context),
            if (isHeight) LayoutParams.WRAP_CONTENT else space,
            if (isHeight) space else LayoutParams.WRAP_CONTENT
        )
    }
}