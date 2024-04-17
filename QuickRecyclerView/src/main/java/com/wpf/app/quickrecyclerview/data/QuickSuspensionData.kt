package com.wpf.app.quickrecyclerview.data

/**
 * 可以悬浮的Item
 */
open class QuickSuspensionData @JvmOverloads constructor(
    var isSuspension: Boolean = false       //View是否悬浮置顶
) : QuickItemData()