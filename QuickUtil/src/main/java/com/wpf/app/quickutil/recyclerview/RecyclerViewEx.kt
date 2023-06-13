package com.wpf.app.quickutil.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by 王朋飞 on 2022/6/22.
 */

fun RecyclerView.isScrollBottom(): Boolean {
    return !canScrollVertically(1)
}

fun RecyclerView.isScrollTop(): Boolean {
    return !canScrollVertically(-1)
}

fun RecyclerView.scrollToPositionAndOffset(position: Int) {
    if (position != -1) {
        scrollToPosition(position)
        val mLayoutManager = layoutManager
        if (mLayoutManager is LinearLayoutManager) {
            mLayoutManager.scrollToPositionWithOffset(position, 0)
        }
    }
}

fun RecyclerView.itemDecorationPos(itemDecoration: RecyclerView.ItemDecoration): Int {
    for (i in 0 until itemDecorationCount) {
        if (itemDecoration == getItemDecorationAt(i)) return i
    }
    return -1
}
