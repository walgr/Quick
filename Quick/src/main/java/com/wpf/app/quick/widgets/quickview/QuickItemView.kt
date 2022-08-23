package com.wpf.app.quick.widgets.quickview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import kotlin.math.abs

/**
 * Created by 王朋飞 on 2022/8/23.
 *
 */
open class QuickItemView @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    open var viewType: Int = 0
) : View(mContext, attributeSet, defStyleAttr) {

    init {
        initViewType()
    }

    open fun initViewType() {
        if (viewType == 0) {
            viewType = abs(javaClass.name.hashCode())
        }
    }
}