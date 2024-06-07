package com.wpf.app.quickbind.viewpager2

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.wpf.app.quickbind.viewpager.ViewPagerSize

object ViewPagerHelper {

    @SuppressLint("NotifyDataSetChanged")
    fun notifyPagerSize(viewPager: ViewPager?, size: Int) {
        if (viewPager == null) return
        val fragmentsAdapter: ViewPagerSize = viewPager.adapter as? ViewPagerSize ?: return
        fragmentsAdapter.setPageSize(size)
        (viewPager.adapter as PagerAdapter).notifyDataSetChanged()
    }

    @Suppress("unused")
    @SuppressLint("NotifyDataSetChanged")
    fun notifyPagerSize(viewPager: ViewPager2?, size: Int) {
        if (viewPager == null) return
        val fragmentsAdapter: ViewPagerSize = viewPager.adapter as? ViewPagerSize ?: return
        fragmentsAdapter.setPageSize(size)
        (viewPager.adapter as RecyclerView.Adapter<*>).notifyDataSetChanged()
    }
}