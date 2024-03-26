package com.wpf.app.quickwork.ability

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import com.google.android.material.tabs.TabLayout
import com.wpf.app.quickutil.helper.matchWrapLayoutParams

fun ViewGroup.tabLayout(
    layoutParams: LayoutParams = matchWrapLayoutParams,
    build: (TabLayout.() -> Unit)? = null
): TabLayout {
    val tabLayout = TabLayout(context)
    build?.invoke(tabLayout)
    addView(tabLayout, layoutParams)
    return tabLayout
}