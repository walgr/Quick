package com.wpf.app.quickrecyclerview.helper

import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.wpf.app.quickrecyclerview.listeners.RefreshView
import com.wpf.app.quickutil.utils.getChild

fun SmartRefreshLayout.bindRefreshView(autoRefresh: Boolean) {
    val refreshView: RefreshView? = this.getChild {
        it is RefreshView
    }
    this.setOnRefreshListener { refreshView?.onRefresh() }
    this.setOnLoadMoreListener { refreshView?.onLoadMore() }
    if (autoRefresh) {
        this.autoRefresh()
    }
}

fun SmartRefreshLayout.afterRequest(isRefresh: Boolean) {
    if (isRefresh) {
        finishRefresh()
    } else {
        finishLoadMore()
    }
}