package com.wpf.app.quickbind.viewpager

import androidx.viewpager.widget.PagerAdapter

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface ViewPagerSize {

    fun getPageSize(): Int?

    fun setPageSize(size: Int)

    fun getAdapter(): PagerAdapter?

    fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_UNCHANGED
    }

    fun notifyPagerSize(viewPager: ViewPagerSize?, size: Int) {
        if (viewPager == null) return
        val fragmentsAdapter: PagerAdapter = viewPager.getAdapter() ?: return
        setPageSize(size)
        fragmentsAdapter.notifyDataSetChanged()
    }
    
    fun currentContext(): Any? {
        return null
    }
}