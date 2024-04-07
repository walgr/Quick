package com.wpf.app.quick.ability.ex.base

import com.wpf.app.base.ViewLifecycle
import com.wpf.app.base.ability.base.QuickViewAbility

interface QuickLifecycleAbility : QuickViewAbility, ViewLifecycle {
    override fun getPrimeKey() = "lifecycle"
}
