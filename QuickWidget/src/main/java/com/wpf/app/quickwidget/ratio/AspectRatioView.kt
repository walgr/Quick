package com.wpf.app.quickwidget.ratio

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickwidget.R

/**
 * 长宽比Layout
 */
open class AspectRatioView @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(
    mContext, attrs, defStyleAttr
) {

    private var attrsData: AspectRatioViewHelper

    init {
        attrsData = AutoGetAttributeHelper.init(mContext, attrs, R.styleable.AspectRatioView)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        if (attrsData.isWidthMain) {
            //以宽度为主
            super.onMeasure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(
                    (specWidth * attrsData.ratio).toInt(),
                    MeasureSpec.EXACTLY
                )
            )
        } else {
            //以高度为主
            super.onMeasure(
                MeasureSpec.makeMeasureSpec(
                    (specHeight * (1 / attrsData.ratio).toInt()),
                    MeasureSpec.EXACTLY
                ), heightMeasureSpec
            )
        }
    }
}

class AspectRatioViewHelper {

    var ratio: Float = 1f

    var isWidthMain: Boolean = true
}