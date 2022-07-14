package com.wpf.app.quick.widgets.recyclerview

import androidx.databinding.BaseObservable
import kotlin.math.abs

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickItemData(
    open var viewType: Int = 0
): BaseObservable() {

    init {
        initViewType()
    }

    private fun initViewType() {
        if (viewType == 0) {
            viewType = abs(javaClass.name.hashCode())
        }
    }
}