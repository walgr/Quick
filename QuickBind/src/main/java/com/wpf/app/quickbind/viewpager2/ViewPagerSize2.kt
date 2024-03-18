package com.wpf.app.quickbind.viewpager2

import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wpf.app.quickbind.viewpager.ViewPagerSize

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface ViewPagerSize2: ViewPagerSize {

    override fun getAdapter(): PagerAdapter? {
        return null
    }
    fun getAdapter2(): FragmentStateAdapter?
    fun notifyPagerSize(viewPager: ViewPagerSize2?, size: Int) {
        if (viewPager == null) return
        val fragmentsAdapter: FragmentStateAdapter = viewPager.getAdapter2() ?: return
        setPageSize(size)
        fragmentsAdapter.notifyDataSetChanged()
    }
}