package com.wpf.app.quick.widgets.shadow.base

import android.content.Context
import android.util.AttributeSet
import com.wpf.app.quick.R
import com.wpf.app.quickutil.helper.attribute.AutoGetAttribute

open class ShadowViewAttr(
    context: Context,
    attrs: AttributeSet? = null,
) : AutoGetAttribute(context, attrs, R.styleable.ViewShadow) {
    var key: String? = null
    var bindTypes: String? = null
}