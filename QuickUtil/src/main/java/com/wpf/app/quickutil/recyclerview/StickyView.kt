package com.wpf.app.quickutil.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface StickyView {
    /**
     * 是否是吸附view
     * @param curView
     * @return
     */
    fun isStickyView(recyclerView: RecyclerView, curView: View?): Boolean

    /**
     * 得到上一个吸附View
     */
    fun getLastStickyView(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?, curViewPos: Int): Int

    /**
     * 得到吸附view的itemType
     * @return
     */
    fun getStickViewType(recyclerView: RecyclerView, view: View?): Int
}