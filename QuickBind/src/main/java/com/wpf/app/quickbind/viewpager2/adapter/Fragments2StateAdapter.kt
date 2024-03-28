package com.wpf.app.quickbind.viewpager2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wpf.app.quickbind.viewpager2.ViewPagerSize2

open class Fragments2StateAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private var getFragment: ((position: Int) -> Fragment),
) : FragmentStateAdapter(fragmentManager, lifecycle), ViewPagerSize2 {

    override fun getItemCount(): Int {
        return getPageSize()
    }

    override fun createFragment(position: Int): Fragment {
        return getFragment.invoke(position)
    }

    private var pageSizeI = -1
    override fun getPageSize(): Int {
        return pageSizeI
    }

    override fun setPageSize(size: Int) {
        pageSizeI = size
    }

    private var itemPosition = PagerAdapter.POSITION_UNCHANGED
    override fun setItemPosition(itemPosition: Int) {
        this.itemPosition = itemPosition
    }

    override fun getItemPosition(`object`: Any): Int {
        return itemPosition
    }

    override fun getAdapter2(): FragmentStateAdapter {
        return this
    }
}