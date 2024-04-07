package com.wpf.app.quick.ability.ex.base

import com.wpf.app.base.ability.base.QuickViewAbility

interface QuickFragmentAbility : QuickViewAbility {
    override fun getPrimeKey() = "fragment"
    fun setUserVisibleHint(isVisibleToUser: Boolean) {

    }
}