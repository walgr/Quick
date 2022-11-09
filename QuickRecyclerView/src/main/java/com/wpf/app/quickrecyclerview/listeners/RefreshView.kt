package com.wpf.app.quickrecyclerview.listeners

interface RefreshView {

    var refreshView: RefreshView?

    fun onRefresh() {}

    fun onLoadMore() {}
}