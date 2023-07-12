package com.wpf.app.quickrecyclerview.data

import java.io.Serializable
import kotlin.math.abs

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickItemData @JvmOverloads constructor(
    @Transient open val isSuspension: Boolean = false                 //View是否悬浮置顶
): Serializable {
    var viewType: Int = 0
    init {
        initViewType()
    }

    open fun initViewType() {
        if (viewType == 0) {
            viewType = abs(javaClass.name.hashCode())
        }
    }
}

fun QuickItemData?.and(other: QuickItemData) : List<QuickItemData>? {
    if (this == null) return null
    return arrayListOf(this, other)
}

fun QuickItemData?.and(otherList: List<QuickItemData>) : List<QuickItemData>? {
    if (this == null) return null
    return arrayListOf(this).apply {
        addAll(otherList)
    }
}