package com.wpf.app.base.ability.base

import android.content.Intent
import android.os.Bundle
import com.wpf.app.base.Quick
import com.wpf.app.base.ViewLifecycle

interface AbilityLifecycle: ViewLifecycle {
    fun onCreate(owner: Quick) {}

    fun onStart(owner: Quick) {}

    fun onResume(owner: Quick) {}

    fun onPause(owner: Quick) {}

    fun onStop(owner: Quick) {}

    fun onDestroy(owner: Quick) {}

    fun onSaveInstanceState(owner: Quick, outState: Bundle) {}

    fun onActivityResult(owner: Quick, requestCode: Int, resultCode: Int, data: Intent?) {}
}

interface QuickLifecycleAbility : QuickInitViewAbility, AbilityLifecycle {
    override fun getPrimeKey() = "lifecycle"
}
