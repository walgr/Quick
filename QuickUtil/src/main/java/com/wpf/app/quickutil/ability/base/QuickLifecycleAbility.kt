package com.wpf.app.quickutil.ability.base

import android.content.Intent
import android.os.Bundle
import com.wpf.app.quickutil.Quick
import com.wpf.app.quickutil.ViewLifecycle

interface AbilityLifecycle: ViewLifecycle {
    fun onCreate(owner: Quick) {}

    fun onStart(owner: Quick) {}

    fun onResume(owner: Quick) {}

    fun onPause(owner: Quick) {}

    fun onStop(owner: Quick) {}

    fun onDestroy(owner: Quick) {}

    fun onSaveInstanceState(owner: Quick, outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
    fun onRestoredInstanceState(owner: Quick, savedInstanceState: Bundle?) {
        super.onRestoredInstanceState(savedInstanceState)
    }

    fun onActivityResult(owner: Quick, requestCode: Int, resultCode: Int, data: Intent?) {}
}

interface QuickLifecycleAbility : QuickInitViewAbility, AbilityLifecycle {
    override fun getPrimeKey() = "lifecycle"
}
