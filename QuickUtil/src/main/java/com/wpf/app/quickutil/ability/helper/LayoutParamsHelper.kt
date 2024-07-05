package com.wpf.app.quickutil.ability.helper

import android.view.ViewGroup
import com.wpf.app.quickutil.ability.scope.ViewGroupScope
import com.wpf.app.quickutil.ability.scope.viewGroupApply
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickutil.helper.smartLayoutParams

@Suppress("unused")
fun ViewGroupScope<out ViewGroup>.smartLayoutParams(layoutParams: ViewGroup.LayoutParams = matchMarginLayoutParams()): ViewGroup.LayoutParams {
    viewGroupApply {
        return this.smartLayoutParams(layoutParams)
    }
}