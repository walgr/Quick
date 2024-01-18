package com.wpf.app.quickwidget.shadow.base

import android.content.Context
import android.util.AttributeSet
import com.wpf.app.quickutil.helper.attribute.AutoGetAttribute
import com.wpf.app.quickwidget.R

open class ShadowViewAttr(
    context: Context,
    attrs: AttributeSet? = null,
) : AutoGetAttribute(context, attrs, R.styleable.ViewShadow) {
    var key: String? = null
    var bindTypes: String? = null
}