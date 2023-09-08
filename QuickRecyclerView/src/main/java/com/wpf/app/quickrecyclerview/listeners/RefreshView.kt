package com.wpf.app.quickrecyclerview.listeners

import androidx.recyclerview.widget.RecyclerView


interface RefreshView {

    var refreshView: RefreshView?

    fun getAdapter(): RecyclerView.Adapter<*> {
        return null!!
    }

    fun onRefresh() {}

    fun onLoadMore() {}

    fun onRefreshEnd(data: List<*>?) {}
    fun onLoadMoreEnd(data: List<*>?) {}
}