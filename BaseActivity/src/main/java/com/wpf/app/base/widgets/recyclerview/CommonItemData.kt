package com.wpf.app.base.widgets.recyclerview

import androidx.databinding.BaseObservable
import androidx.lifecycle.ViewModel
import kotlin.math.abs


/**
 * Created by 王朋飞 on 2022/5/11.
 * ViewHolder基础数据
 */
open class CommonItemData(
    open var id: Int = 0,
    open var isSelect: Boolean = false,
    var viewType: Int = 0
): BaseObservable() {

    init {
        if (viewType == 0) {
            viewType = abs(this::class.java.name.hashCode())
        }
    }
}