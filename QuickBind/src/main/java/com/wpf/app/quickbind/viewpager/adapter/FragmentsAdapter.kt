package com.wpf.app.quickbind.viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.wpf.app.quickbind.viewpager.ViewPagerSize

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
open class FragmentsAdapter(
    fm: FragmentManager, private val getFragment: (position: Int) -> Fragment,
) : FragmentPagerAdapter(fm), ViewPagerSize {

    override fun getItem(i: Int): Fragment {
        return getFragment.invoke(i)
    }

    override fun getCount(): Int {
        return getPageSize()
    }

    private var pageSizeI = -1
    override fun getPageSize(): Int {
        return pageSizeI
    }
    override fun setPageSize(size: Int) {
        pageSizeI = size
    }

    override fun getAdapter() = this

    private var itemPositionChange: ((`object`: Any) -> Int)? = {
        PagerAdapter.POSITION_UNCHANGED
    }
    override fun registerItemPositionChange(change: (`object`: Any) -> Int) {
        this.itemPositionChange = change
    }
    override fun getItemPosition(`object`: Any): Int {
        return itemPositionChange?.invoke(`object`) ?: super.getItemPosition(`object`)
    }

    private var itemIdChange: ((position: Int) -> Long)? = null
    override fun registerItemIdChange(change: (position: Int) -> Long) {
        this.itemIdChange = change
    }
    override fun getItemId(position: Int): Long {
        return itemIdChange?.invoke(position) ?: super.getItemId(position)
    }
}