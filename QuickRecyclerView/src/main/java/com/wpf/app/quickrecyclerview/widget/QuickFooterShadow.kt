package com.wpf.app.quickrecyclerview.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.wpf.app.quickutil.widget.QuickViewGroup

class QuickFooterShadow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
): QuickViewGroup<FrameLayout>(context, attrs, defStyleAttr, addToParent = false, forceGenerics = true)