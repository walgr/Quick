package com.wpf.app.quickbind.viewpager2

import androidx.viewpager2.widget.ViewPager2
import com.wpf.app.quickbind.viewpager2.adapter.FragmentStateAdapter

object ViewPager2Helper {

    fun notifyPagerSize(viewPager: ViewPager2?, size: Int) {
        if (viewPager == null) return
        val fragmentsAdapter: FragmentStateAdapter = viewPager.adapter as? FragmentStateAdapter ?: return
        fragmentsAdapter.setPageSize(size)
        fragmentsAdapter.notifyDataSetChanged()
    }
}