package com.wpf.app.quickrecyclerview.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.wpf.app.quickrecyclerview.R
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper

class QuickHeaderShadow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    var isSuspension: Boolean = false,
    var isMatch: Boolean = true,
) : FrameLayout(
    context,
    attrs,
    defStyleAttr
) {
    init {
        AutoGetAttributeHelper.init(context, attrs, R.styleable.QuickHeaderShadow, HeaderShadowAttrs()).apply {
            this@QuickHeaderShadow.isSuspension = this.isSuspension
            this@QuickHeaderShadow.isMatch = this.isMatch
        }
    }

    inner class HeaderShadowAttrs(
        val isSuspension: Boolean = false,
        val isMatch: Boolean = true
    )
}