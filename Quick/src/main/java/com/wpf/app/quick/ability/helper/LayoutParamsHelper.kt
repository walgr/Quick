package com.wpf.app.quick.ability.helper

import android.view.ViewGroup
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.base.ability.scope.viewGroupApply
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.widget.smartLayoutParams

fun ViewGroupScope<out ViewGroup>.smartLayoutParams(layoutParams: ViewGroup.LayoutParams = matchLayoutParams()): ViewGroup.LayoutParams {
    viewGroupApply {
        return this.smartLayoutParams(layoutParams)
    }
}