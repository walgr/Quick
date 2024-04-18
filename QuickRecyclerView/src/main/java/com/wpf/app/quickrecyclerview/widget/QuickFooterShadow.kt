package com.wpf.app.quickrecyclerview.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.wpf.app.quickrecyclerview.R
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper

class QuickFooterShadow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    var isMatch: Boolean = true,
): FrameLayout(context, attrs, defStyleAttr) {
    init {
        AutoGetAttributeHelper.init(context, attrs, R.styleable.QuickFooterShadow, FooterShadowAttrs()).apply {
            this@QuickFooterShadow.isMatch = this.isMatch
        }
    }

    inner class FooterShadowAttrs(
        val isMatch: Boolean = true
    )
}