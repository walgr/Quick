package com.wpf.app.quickrecyclerview.helper

import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.wpf.app.quickrecyclerview.listeners.RefreshResult
import com.wpf.app.quickutil.helper.getChild

fun SmartRefreshLayout.bindRefreshView(autoRefresh: Boolean) {
    val refreshResult: RefreshResult? = this.getChild {
        it is RefreshResult
    }
    this.setOnRefreshListener { refreshResult?.onRefresh() }
    this.setOnLoadMoreListener { refreshResult?.onLoadMore() }
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