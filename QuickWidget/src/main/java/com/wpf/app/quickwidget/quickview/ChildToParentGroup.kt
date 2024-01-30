package com.wpf.app.quickwidget.quickview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import com.wpf.app.quickutil.helper.removeParent

/**
 * 子View添加到此View的父View上
 * 可以在此类中做逻辑给子View附加逻辑
 */
open class ChildToParentGroup @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : Only1Child<ViewGroup>(
    mContext, attributeSet, defStyleAttr
) {

    private fun addTToParent() {
        val parentGroup = parent as? ViewGroup ?: return
        val position = parentGroup.indexOfChild(this)
        val childView = getChildAt(0)
        childView.removeParent()
        parentGroup.removeView(this)
        parentGroup.addView(childView, position, layoutParams)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        getChildAt(0)?.let {
            it.measure(widthMeasureSpec, heightMeasureSpec)
            val viewMeasureWidth = it.measuredWidth
            val viewMeasureHeight = it.measuredHeight
            val specModeWidth = MeasureSpec.getMode(widthMeasureSpec)
            val specModeHeight = MeasureSpec.getMode(heightMeasureSpec)
            setMeasuredDimension(
                MeasureSpec.makeMeasureSpec(viewMeasureWidth, specModeWidth),
                MeasureSpec.makeMeasureSpec(viewMeasureHeight, specModeHeight)
            )
            addTToParent()
        } ?: {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        getChildAt(0)?.let {
            it.layout(0, 0, it.measuredWidth, it.measuredHeight)
        }
    }
}