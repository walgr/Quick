package com.wpf.app.quickwidget.emptyview

import com.wpf.app.quickrecyclerview.listeners.RefreshView
import com.wpf.app.quickutil.log.LogUtil

object EmptyHelper {

    fun bind(view: RefreshView?, customRefreshView: RefreshView? = null, emptyView: BaseEmptyView?) {
        if (view == null || emptyView == null) return
        view.refreshView = customRefreshView ?: object : RefreshView by view {

            override fun onRefresh() {
                super.onRefresh()
                LogUtil.e("空数据页", "下拉刷新")
                emptyView.changeState(StateLoading)
            }

            override fun onLoadMore() {
                super.onLoadMore()
                LogUtil.e("空数据页", "上拉加载")
            }

            override fun onRefreshEnd(data: List<*>?) {
                super.onRefreshEnd(data)
                LogUtil.e("空数据页", "刷新结束")
                if (data.isNullOrEmpty()) {
                    emptyView.changeState(StateEmptyData)
                } else {
                    emptyView.changeState(StateNoError)
                }
            }

            override fun onLoadMoreEnd(data: List<*>?) {
                super.onLoadMoreEnd(data)
                LogUtil.e("空数据页", "加载结束")
            }
        }
    }
}