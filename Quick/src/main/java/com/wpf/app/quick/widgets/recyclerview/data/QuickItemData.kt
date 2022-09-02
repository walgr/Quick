package com.wpf.app.quick.widgets.recyclerview.data

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

    open fun initViewType() {
        if (viewType == 0) {
            viewType = abs(javaClass.name.hashCode())
        }
    }
}