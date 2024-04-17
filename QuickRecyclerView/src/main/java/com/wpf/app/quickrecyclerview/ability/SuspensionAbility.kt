package com.wpf.app.quickrecyclerview.ability

import android.view.ViewGroup
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.quickrecyclerview.ability.base.QuickItemAbility
import com.wpf.app.quickrecyclerview.data.QuickAbilityData

fun suspension(): MutableList<QuickAbility> {
    return mutableListOf(object : QuickItemAbility<QuickAbilityData> {
        override fun getPrimeKey() = "suspension"

        override fun beforeAdapterCreateHolder(mParent: ViewGroup, selfOnlyBase: QuickAbilityData) {
            super.beforeAdapterCreateHolder(mParent, selfOnlyBase)
            selfOnlyBase.isSuspension = true
        }
    })
}