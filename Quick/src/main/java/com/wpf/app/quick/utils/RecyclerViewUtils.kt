package com.wpf.app.quick.utils

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
}