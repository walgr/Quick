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
    attrs: AttributeSet
) : ViewPager(mContext, attrs), ViewPagerSize {

    private var pageSizeI: Int? = null

    override fun setPageSize(size: Int) {
        pageSizeI = size
    }

    override fun getPageSize(): Int? {
        return pageSizeI
    }

    open fun notifyPagerSize(size: Int) {
        this.pageSizeI = size
        adapter?.notifyDataSetChanged()
    }

    override fun setCurrentItem(item: Int) {
        post { super.setCurrentItem(item) }
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        post { super.setCurrentItem(item, smoothScroll) }
    }
}