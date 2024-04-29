package com.wpf.app.quickrecyclerview.utils

import androidx.recyclerview.widget.RecyclerView

interface StickyHelper {
    fun getAllStickyList(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?) : MutableList<Int>
}