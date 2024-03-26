package com.wpf.app.quickbind.viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.wpf.app.quickbind.viewpager.ViewPagerSize

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
class FragmentsAdapter(
    fm: FragmentManager, private val fragments: List<Fragment>
) : FragmentPagerAdapter(fm), ViewPagerSize {

    override fun getItem(i: Int): Fragment {
        return fragments[i]
    }

    override fun getCount(): Int {
        return if (getPageSize() != -1) {
            getPageSize()
        } else {
            fragments.size
        }
    }

    private var pageSizeI = -1
    override fun getPageSize(): Int {
        return pageSizeI
    }

    override fun setPageSize(size: Int) {
        pageSizeI = size
    }

    override fun getAdapter() = this
    override fun getItemPosition(`object`: Any): Int {
        return super<FragmentPagerAdapter>.getItemPosition(`object`)
    }
}