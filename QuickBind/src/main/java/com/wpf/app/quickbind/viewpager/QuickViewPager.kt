package com.wpf.app.quickbind.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.wpf.app.quickbind.R
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
open class QuickViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    var canScroll: Boolean = true,
) : ViewPager(context, attrs) {

    init {
        AutoGetAttributeHelper.init(context, attrs, R.styleable.QuickViewPager, QuickViewPagerAttr())?.apply {
            this@QuickViewPager.canScroll = this.canScroll
        }
    }

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