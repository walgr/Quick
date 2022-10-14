package com.wpf.app.quickutil.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface StickyView {
    /**
     * 是否是吸附view
     * @param view
     * @return
     */
    fun isStickyView(recyclerView: RecyclerView, view: View?): Boolean

    /**
     * 得到吸附view的itemType
     * @return
     */
    fun getStickViewType(recyclerView: RecyclerView, view: View?): Int
}