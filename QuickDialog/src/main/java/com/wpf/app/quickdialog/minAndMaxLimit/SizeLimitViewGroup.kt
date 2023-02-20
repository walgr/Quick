package com.wpf.app.quickdialog.minAndMaxLimit

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class SizeLimitViewGroup @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(mContext, attributeSet, defStyleAttr), MinAndMaxLimit {

    override var maxWidth: Int = 0           // 最大宽度
    override var maxHeight: Int = 0          // 最大高度

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        when {
            maxWidth != 0 -> {
                widthSize = if (widthSize > maxWidth) maxWidth else widthSize
            }
            maxHeight != 0 -> {
                heightSize = if (heightSize > maxHeight) maxHeight else heightSize
            }
        }

        val widthSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode)
        val heightSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode)
        super.onMeasure(widthSpec, heightSpec)
    }

    var firstChildView: View? = null

    override fun getFirstChild(): View? {
        if (firstChildView == null) {
            firstChildView = getChildAt(0)
        }
        return firstChildView
    }
}