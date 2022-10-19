package com.wpf.app.quickrecyclerview.data

import androidx.databinding.BaseObservable
import kotlin.math.abs

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickItemData(
    open var viewType: Int = 0,
    open val isSuspension: Boolean = false                 //View是否悬浮置顶
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