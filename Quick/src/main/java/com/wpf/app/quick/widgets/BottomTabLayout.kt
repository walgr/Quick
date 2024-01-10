package com.wpf.app.quick.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.wpf.app.quickutil.widgets.quickview.QuickViewGroup
import kotlinx.coroutines.InternalCoroutinesApi

class BottomTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
): QuickViewGroup<LinearLayout>(context, attrs, defStyleAttr, addToParent = true) {

    init {

    }
}