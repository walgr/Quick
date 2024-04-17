package com.wpf.app.quickrecyclerview.data

import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/7/13.
 * 基类Item
 */
open class QuickItemData(
    var viewType: Int = 0
): Serializable {
    init {
        this.initViewType()
    }

    open fun initViewType() {
        if (viewType == 0) {
            viewType = javaClass.name.hashCode()
        }
    }
}