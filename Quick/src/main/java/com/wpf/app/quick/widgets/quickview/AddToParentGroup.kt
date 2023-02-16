package com.wpf.app.quick.widgets.quickview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

open class AddToParentGroup<T: ViewGroup>(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
): QuickViewGroup<T>(
    mContext, attributeSet, defStyleAttr, addToParent = true
)