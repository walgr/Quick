package com.wpf.app.quick.widgets.quickview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

open class AddToThisGroup @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : Only1Child(
    mContext, attributeSet, defStyleAttr
) {

    private fun addTToThis() {
        if (isInEditMode) {
            layoutParams = getChildAt(0).layoutParams
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val it = getChildAt(0)
        addTToThis()
        it.measure(widthMeasureSpec, heightMeasureSpec)
        val viewMeasureWidth = it.measuredWidth
        val viewMeasureHeight = it.measuredHeight
        val specModeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val specModeHeight = MeasureSpec.getMode(heightMeasureSpec)
        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(viewMeasureWidth, specModeWidth),
            MeasureSpec.makeMeasureSpec(viewMeasureHeight, specModeHeight)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val it = getChildAt(0)
        it.layout(0, 0, it.measuredWidth, it.measuredHeight)
    }

}