package com.wpf.app.quick.ability.ex.base

import com.wpf.app.base.ability.base.QuickInitViewAbility

interface QuickFragmentAbility : QuickInitViewAbility {
    override fun getPrimeKey() = "fragment"
    fun setUserVisibleHint(isVisibleToUser: Boolean) {

    }
}