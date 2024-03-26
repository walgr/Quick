package com.wpf.app.quickutil.widget

import com.google.android.material.tabs.TabLayout

fun TabLayout.onTabSelected(onTabSelected: TabLayout.Tab.(position: Int) -> Unit) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let {
                onTabSelected.invoke(tab, selectedTabPosition)
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

        }

        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

    })
}