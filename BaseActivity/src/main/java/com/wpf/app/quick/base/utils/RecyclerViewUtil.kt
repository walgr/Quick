package com.wpf.app.quick.base.utils

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by 王朋飞 on 2022/5/19.
 * RecyclerView工具类
 */

fun RecyclerView.isScrollBottom(): Boolean {
    return !this.canScrollVertically(1)
}

fun RecyclerView.isScrollTop(): Boolean {
    return !this.canScrollVertically(-1)
}
