package com.wpf.app.quick.helper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.wpf.app.quick.activity.QuickView
import com.wpf.app.quickutil.other.forceTo

fun QuickView.getFragmentManager(): FragmentManager {
    return if (this is Fragment) {
        childFragmentManager
    } else {
        this.forceTo<FragmentActivity>().supportFragmentManager
    }
}

fun QuickView.getLifecycle(): Lifecycle {
    return if (this is Fragment) {
        lifecycle
    } else {
        this.forceTo<FragmentActivity>().lifecycle
    }
}