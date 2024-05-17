package com.wpf.app.quickbind.viewpager

import androidx.viewpager.widget.PagerAdapter
import com.wpf.app.quickutil.other.forceTo

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface ViewPagerSize {

    fun getPageSize(): Int?

    fun setPageSize(size: Int)

    fun getAdapter(): PagerAdapter?

    fun registerItemPositionChange(change: (`object`: Any) -> Int) {

    }

    fun registerItemIdChange(change: (position: Int) -> Long) {

    }

    fun notifyPagerSize(size: Int) {
        setPageSize(size)
        forceTo<PagerAdapter>().notifyDataSetChanged()
    }
}