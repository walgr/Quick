package com.wpf.app.quickrecyclerview.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface StickyView {
    /**
     * 是否是吸附view
     * @param curView
     * @return
     */
    fun isStickyView(recyclerView: RecyclerView, curView: View?): Boolean


    fun getAllStickyList(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?) : List<Int>

    /**
     * 得到吸附view的itemType
     * @return
     */
    fun getStickViewType(recyclerView: RecyclerView, view: View?): Int
}