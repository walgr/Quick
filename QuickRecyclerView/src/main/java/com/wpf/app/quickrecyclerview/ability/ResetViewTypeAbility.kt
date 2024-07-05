package com.wpf.app.quickrecyclerview.ability

import com.wpf.app.quickutil.ability.base.QuickAbility
import com.wpf.app.quickrecyclerview.ability.base.QuickViewTypeAbility

fun resetViewType(
    reset: (position: Int) -> Int
): MutableList<QuickAbility> {
    return mutableListOf(
        object : QuickViewTypeAbility {
            override fun getPrimeKey() = "resetViewType"

            override fun initViewType(position: Int): Int {
                return reset.invoke(position)
            }
        })
}