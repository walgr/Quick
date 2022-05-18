package com.wpf.app.quick.base.widgets.recyclerview

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import kotlin.math.abs

/**
 * Created by 王朋飞 on 2022/5/11.
 * ViewHolder基础数据
 */
open class CommonItemData(
    open var id: String = "",
    open var isSelect: MutableLiveData<Boolean> = MutableLiveData(false),
    var viewType: Int = 0
): BaseObservable() {

    init {
        if (viewType == 0) {
            viewType = abs(this::class.java.name.hashCode())
        }
    }
}