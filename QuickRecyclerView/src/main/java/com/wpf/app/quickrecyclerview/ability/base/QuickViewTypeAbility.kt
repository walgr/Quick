package com.wpf.app.quickrecyclerview.ability.base

import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.Unique

interface QuickViewTypeAbility: QuickAbility, Unique {
    fun initViewType(position: Int): Int
}