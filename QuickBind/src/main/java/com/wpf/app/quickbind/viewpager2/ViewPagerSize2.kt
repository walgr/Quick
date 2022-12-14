package com.wpf.app.quickbind.viewpager2

import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface ViewPagerSize2 {

    fun getPageSize(): Int?

    fun setPageSize(size: Int)

    fun getAdapter(): FragmentStateAdapter?

    fun notifyPagerSize(viewPager: ViewPagerSize2?, size: Int) {
        if (viewPager == null) return
        val fragmentsAdapter: FragmentStateAdapter = viewPager.getAdapter() ?: return
        setPageSize(size)
        fragmentsAdapter.notifyDataSetChanged()
    }
    
    fun currentContext(): Any? {
        return null
    }
}