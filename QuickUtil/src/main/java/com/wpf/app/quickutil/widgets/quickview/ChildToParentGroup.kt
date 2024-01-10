package com.wpf.app.quickutil.widgets.quickview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * 子View添加到此View的父View上
 * 可以在此类中做逻辑给子View附加逻辑
 */
open class ChildToParentGroup @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : Only1Child(
    mContext, attributeSet, defStyleAttr
) {

    private fun addTToParent() {
        val parentGroup = parent as? ViewGroup ?: return
        val position = parentGroup.indexOfChild(this)
        val childView = getChildAt(0)
        parentGroup.removeView(this)
        removeView(childView)
        parentGroup.addView(childView, position)
    }

    private fun addTToThis() {
        if (isInEditMode) {
            val it = getChildAt(0)
            layoutParams = when (layoutParams) {
                is RadioGroup.LayoutParams -> {
                    RadioGroup.LayoutParams(it.layoutParams)
                }
                is LinearLayout.LayoutParams -> {
                    LinearLayout.LayoutParams(it.layoutParams)
                }
                is RelativeLayout.LayoutParams -> {
                    RelativeLayout.LayoutParams(it.layoutParams)
                }
                is FrameLayout.LayoutParams -> {
                    FrameLayout.LayoutParams(it.layoutParams)
                }
                is ConstraintLayout.LayoutParams -> {
                    ConstraintLayout.LayoutParams(it.layoutParams)
                }
                else -> {
                    MarginLayoutParams(it.layoutParams)
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val it = getChildAt(0)
        if (it == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        if (isInEditMode) {
            addTToThis()
        } else {
            addTToParent()
        }
        it.measure(widthMeasureSpec, heightMeasureSpec)
        val viewMeasureWidth = it.measuredWidth
        val viewMeasureHeight = it.measuredHeight
        val specModeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val specModeHeight = MeasureSpec.getMode(heightMeasureSpec)
        setMeasuredDimension(
            resolveSize(viewMeasureWidth, specModeWidth),
            resolveSize(viewMeasureHeight, specModeHeight)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val it = getChildAt(0)
        it.layout(0, 0, it.measuredWidth, it.measuredHeight)
    }
}