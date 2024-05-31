package com.wpf.app.quickwidget.emptyview

import com.wpf.app.quickrecyclerview.listeners.RefreshView

object EmptyHelper {

    fun bind(view: RefreshView?, customRefreshView: RefreshView? = null, emptyView: BaseEmptyView?) {
        if (view == null || emptyView == null) return
        view.refreshView = customRefreshView ?: object : RefreshView by view {

            override fun onRefresh() {
                super.onRefresh()
                emptyView.changeState(StateLoading.new(this.getAdapter().itemCount == 0))
            }

            override fun onLoadMore() {
                super.onLoadMore()
            }

            override fun onRefreshEnd(data: List<*>?) {
                super.onRefreshEnd(data)
                if (data.isNullOrEmpty()) {
                    emptyView.changeState(StateEmptyData)
                } else {
                    emptyView.changeState(StateNoError)
                }
            }
        }
    }
}