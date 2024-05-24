package com.wpf.app.base.ability.helper

import android.view.ViewGroup
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.base.ability.scope.viewGroupApply
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickutil.widget.smartLayoutParams

fun ViewGroupScope<out ViewGroup>.smartLayoutParams(layoutParams: ViewGroup.LayoutParams = matchMarginLayoutParams()): ViewGroup.LayoutParams {
    viewGroupApply {
        return this.smartLayoutParams(layoutParams)
    }
}