package com.wpf.app.quickrecyclerview.data

open class QuickHeaderData(
    isSuspension: Boolean = false,       //View是否悬浮置顶
    val isMatch: Boolean = true,             //是否占满
) : QuickViewData(isSuspension = isSuspension)