package com.wpf.app.quickwork.ability

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import com.google.android.material.tabs.TabLayout
import com.wpf.app.quick.ability.ex.smartLayoutParams
import com.wpf.app.quickutil.helper.toView

fun ViewGroup.tabLayout(
    tabLayoutRes: Int = 0,
    tabSize: Int = 0,
    layoutParams: LayoutParams = smartLayoutParams(),
    tabInit: ((position: Int, tabView: View) -> Unit)? = null,
    builder: (TabLayout.() -> Unit)? = null
): TabLayout {
    val tabLayout = TabLayout(context)
    if (tabSize > 0) {
        repeat(tabSize) {
            val tab = tabLayout.newTab()
            val tabView = tabLayoutRes.toView(context)
            tab.setCustomView(tabView)
            tabInit?.invoke(it, tabView)
            tabLayout.addTab(tab)
        }
    }
    builder?.invoke(tabLayout)
    addView(tabLayout, layoutParams)
    return tabLayout
}