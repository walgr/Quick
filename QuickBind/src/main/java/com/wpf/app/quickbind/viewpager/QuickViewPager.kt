package com.wpf.app.quickbind.viewpager

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
open class QuickViewPager(
    mContext: Context,
    attrs : AttributeSet
): ViewPager(mContext, attrs), ViewPagerSize {

    var pageSizeI = 0

    override fun setPageSize(pageSize: Int) {
        pageSizeI = pageSize
    }

    override fun getPageSize(): Int {
        return pageSizeI
    }

    open fun notifyPagerSize(size: Int) {
        this.pageSizeI = size
        val fragmentsAdapter = adapter ?: return
        fragmentsAdapter.notifyDataSetChanged()
    }

    override fun setCurrentItem(item: Int) {
        post { super.setCurrentItem(item) }
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        post { super.setCurrentItem(item, smoothScroll) }
    }
}