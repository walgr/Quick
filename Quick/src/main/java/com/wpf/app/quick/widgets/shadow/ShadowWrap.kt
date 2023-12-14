package com.wpf.app.quick.widgets.shadow

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * 影子View
 */
class ShadowWrap<T: View>(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) {

    private lateinit var srcView: T
    fun inShadow(view: T): ShadowWrap<T> {
        srcView = view
        return this
    }
}