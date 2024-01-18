package com.wpf.app.quickwidget.quickview.inxml

import android.content.Context
import android.util.AttributeSet
import com.wpf.app.quickwidget.quickview.ChildToParentGroup

class HttpRequest @JvmOverloads constructor(
    mContext: Context,
    val attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ChildToParentGroup(
    mContext, attributeSet, defStyleAttr
)