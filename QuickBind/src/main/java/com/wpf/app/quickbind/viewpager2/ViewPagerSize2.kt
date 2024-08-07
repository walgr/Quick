package com.wpf.app.quickbind.viewpager2

import android.annotation.SuppressLint
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wpf.app.quickbind.viewpager.ViewPagerSize
import com.wpf.app.quickutil.helper.generic.forceTo

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface ViewPagerSize2: ViewPagerSize {

    override fun getAdapter(): PagerAdapter? {
        return null
    }
    fun getAdapter2(): FragmentStateAdapter?

    @SuppressLint("NotifyDataSetChanged")
    override fun notifyPagerSize(size: Int) {
        setPageSize(size)
        forceTo<FragmentStateAdapter>().notifyDataSetChanged()
    }
}