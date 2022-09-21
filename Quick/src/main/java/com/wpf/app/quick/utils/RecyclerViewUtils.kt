package com.wpf.app.quick.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by 王朋飞 on 2022/6/22.
 */
object RecyclerViewUtils {

    fun isScrollBottom(recyclerView: RecyclerView): Boolean {
        return !recyclerView.canScrollVertically(1)
    }

    fun isScrollTop(recyclerView: RecyclerView): Boolean {
        return !recyclerView.canScrollVertically(-1)
    }

    fun scrollToPosition(recyclerView: RecyclerView?, position: Int) {
        if (position != -1) {
            recyclerView?.scrollToPosition(position)
            val mLayoutManager = recyclerView?.layoutManager
            if (mLayoutManager is LinearLayoutManager) {
                mLayoutManager.scrollToPositionWithOffset(position, 0)
            }
        }
    }
}