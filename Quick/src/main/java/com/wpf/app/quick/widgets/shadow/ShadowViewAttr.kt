package com.wpf.app.quick.widgets.shadow

import android.content.Context
import android.util.AttributeSet
import com.wpf.app.quick.R
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper

open class ShadowViewAttr(
    context: Context,
    attrs: AttributeSet? = null,
) : AutoGetAttributeHelper(context, attrs, R.styleable.ViewShadow) {
    var key: String? = null
}