package com.wpf.app.quickrecyclerview.data

/**
 * 可以悬浮的Item
 */
open class QuickSuspensionData @JvmOverloads constructor(
    @Transient open val isSuspension: Boolean = false       //View是否悬浮置顶
) : QuickItemData()