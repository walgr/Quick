package com.wpf.app.quickrecyclerview.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.wpf.app.quickrecyclerview.R
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickutil.widget.QuickViewGroup

class QuickHeaderShadow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    var isSuspension: Boolean = false,
) : QuickViewGroup<FrameLayout>(
    context,
    attrs,
    defStyleAttr,
    addToParent = false,
    forceGenerics = true
) {
    init {
        AutoGetAttributeHelper.init(context, attrs, R.styleable.QuickHeaderShadow, HeaderShadowAttrs()).apply {
            this@QuickHeaderShadow.isSuspension = this.isSuspension
        }
    }

    class HeaderShadowAttrs(
        val isSuspension: Boolean = false
    )
}