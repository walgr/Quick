package com.wpf.app.base.ability.base

import com.wpf.app.base.ViewLifecycle

interface QuickLifecycleAbility : QuickInitViewAbility, ViewLifecycle {
    override fun getPrimeKey() = "lifecycle"
}
