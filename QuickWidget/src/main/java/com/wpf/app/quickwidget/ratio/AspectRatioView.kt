package com.wpf.app.quickwidget.ratio

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.wpf.app.quickutil.helper.attribute.AutoGetAttribute
import com.wpf.app.quickwidget.R

/**
 * 长宽比Layout
 */
open class AspectRatioView @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(
    mContext, attributeSet, defStyleAttr
) {

    private lateinit var viewHelper: AspectRatioViewHelper

    init {
        attributeSet?.let {
            viewHelper = AspectRatioViewHelper(mContext, it)
            if (viewHelper.ratio == null) {
                viewHelper.ratio = 1.0f
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specModeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specModeHeight = MeasureSpec.getMode(heightMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        if (specModeWidth != MeasureSpec.AT_MOST && specModeWidth != MeasureSpec.UNSPECIFIED && specHeight > specWidth) {
            //以宽度为主
            super.onMeasure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(
                    (specWidth * viewHelper.ratio!!).toInt(),
                    specModeHeight
                )
            )
        } else if (specModeHeight != MeasureSpec.AT_MOST && specModeHeight != MeasureSpec.UNSPECIFIED && specHeight < specWidth) {
            //以高度为主
            super.onMeasure(
                MeasureSpec.makeMeasureSpec(
                    (specHeight * (1 / viewHelper.ratio!!).toInt()),
                    specModeWidth
                ), heightMeasureSpec
            )
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}

class AspectRatioViewHelper(
    context: Context,
    attributeSet: AttributeSet,
) : AutoGetAttribute(context, attributeSet, R.styleable.AspectRatioView) {

    var ratio: Float? = null
}