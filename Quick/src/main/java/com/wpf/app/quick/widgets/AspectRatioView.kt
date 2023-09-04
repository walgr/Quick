package com.wpf.app.quick.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.wpf.app.quick.R
import com.wpf.app.quick.helper.attribute.base.AutoGetAttributeHelper
import com.wpf.app.quick.widgets.quickview.QuickViewGroup

/**
 * 长宽比Layout
 */
open class AspectRatioView @JvmOverloads constructor(
    mContext: Context,
    override val attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : QuickViewGroup<RelativeLayout>(
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
        if (specModeWidth != MeasureSpec.AT_MOST && specModeWidth != MeasureSpec.UNSPECIFIED) {
            //以宽度为主
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((specWidth * viewHelper.ratio!!).toInt(), specModeHeight))
        } else if (specModeHeight != MeasureSpec.AT_MOST && specModeHeight != MeasureSpec.UNSPECIFIED) {
            //以高度为主
            super.onMeasure(MeasureSpec.makeMeasureSpec((specHeight * viewHelper.ratio!!).toInt(), specModeWidth), heightMeasureSpec)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}

class AspectRatioViewHelper(
    context: Context,
    attributeSet: AttributeSet,
) : AutoGetAttributeHelper(context, attributeSet, R.styleable.AspectRatioView) {

    var ratio: Float? = null
}