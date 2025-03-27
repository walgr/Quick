package com.wpf.app.quick.ability

import com.wpf.app.quickutil.ability.base.QuickAbility

interface QuickAbilityI {
    val abilityList: List<QuickAbility>
    fun initAbility(): List<QuickAbility>
}