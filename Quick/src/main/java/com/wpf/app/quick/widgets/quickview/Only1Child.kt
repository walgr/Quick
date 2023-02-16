package com.wpf.app.quick.widgets.quickview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

abstract class Only1Child @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ViewGroup(mContext, attributeSet, defStyleAttr) {

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 1) {
            throw RuntimeException("只允许1个子View")
        }
    }

    //防止丢失子View的LayoutParams
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}