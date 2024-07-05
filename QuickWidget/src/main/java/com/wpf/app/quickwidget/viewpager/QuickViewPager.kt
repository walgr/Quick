package com.wpf.app.quickwidget.viewpager

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.wpf.app.quickbind.R
import com.wpf.app.quickbind.viewpager.ViewPagerSize
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickutil.helper.generic.forceTo

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
open class QuickViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @Suppress("MemberVisibilityCanBePrivate") var canScroll: Boolean = true,
) : ViewPager(context, attrs) {

    init {
        AutoGetAttributeHelper.init(context, attrs, R.styleable.QuickViewPager, QuickViewPagerAttr()).apply {
            this@QuickViewPager.canScroll = this.canScroll
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(arg0: MotionEvent?): Boolean {
        return if (canScroll) {
            super.onTouchEvent(arg0)
        } else {
            false
        }
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent?): Boolean {
        return if (canScroll) {
            super.onInterceptTouchEvent(arg0)
        } else {
            false
        }
    }

    internal class QuickViewPagerAttr(
        val canScroll: Boolean = true
    )
}

@Suppress("unused")
fun QuickViewPager.notifyPagerSize(size: Int) {
    adapter?.forceTo<ViewPagerSize>()?.setPageSize(size)
    adapter?.forceTo<PagerAdapter>()?.notifyDataSetChanged()
}