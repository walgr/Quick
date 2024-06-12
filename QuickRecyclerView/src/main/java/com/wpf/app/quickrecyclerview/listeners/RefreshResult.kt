package com.wpf.app.quickrecyclerview.listeners


interface RefreshResult {
    //view操作
    fun onRefresh() {}
    fun onLoadMore() {}

    //执行请求前
    val onRefresh: MutableList<(curData: List<*>?) -> Unit>
    fun onRefresh(curData: List<*>?) {
        onRefresh.forEach { it.invoke(curData) }
    }

    fun onRefresh(callback: (curData: List<*>?) -> Unit) {
        onRefresh.add(callback)
    }

    val onLoadMore: MutableList<(curData: List<*>?) -> Unit>
    fun onLoadMore(curData: List<*>?) {
        onLoadMore.forEach { it.invoke(curData) }
    }

    fun onLoadMore(callback: (curData: List<*>?) -> Unit) {
        onLoadMore.add(callback)
    }

    //执行请求后
    val onRefreshEnd: MutableList<(data: List<*>?) -> Unit>
    fun onRefreshEnd(data: List<*>?) {
        onRefreshEnd.forEach { it.invoke(data) }
    }

    fun onRefreshEnd(callback: (data: List<*>?) -> Unit) {
        onRefreshEnd.add(callback)
    }
    val onLoadMoreEnd: MutableList<(data: List<*>?) -> Unit>
    fun onLoadMoreEnd(data: List<*>?) {
        onLoadMoreEnd.forEach { it.invoke(data) }
    }

    fun onLoadMoreEnd(callback: (data: List<*>?) -> Unit) {
        onLoadMoreEnd.add(callback)
    }

    val onRefreshError: MutableList<(e: Throwable) -> Unit>
    fun onRefreshError(e: Throwable) {
        onRefreshError.forEach { it.invoke(e) }
    }

    fun onRefreshError(callback: (e: Throwable) -> Unit) {
        onRefreshError.add(callback)
    }

    var onLoadMoreError: MutableList<(e: Throwable) -> Unit>
    fun onLoadMoreError(e: Throwable) {
        onLoadMoreError.forEach { it.invoke(e) }
    }

    fun onLoadMoreError(callback: (e: Throwable) -> Unit) {
        onLoadMoreError.add(callback)
    }
}