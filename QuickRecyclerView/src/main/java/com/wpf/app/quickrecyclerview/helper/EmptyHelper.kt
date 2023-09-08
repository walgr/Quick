package com.wpf.app.quickrecyclerview.helper

import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickrecyclerview.listeners.RefreshView
import com.wpf.app.quickutil.LogUtil
import com.wpf.app.quickutil.widgets.emptyview.BaseEmptyView
import com.wpf.app.quickutil.widgets.emptyview.NetError

object EmptyHelper {

    fun bind(view: RefreshView, customRefreshView: RefreshView? = null, emptyView: BaseEmptyView) {
        view.refreshView = customRefreshView ?: object : RefreshView by view {

            override fun onRefresh() {
                super.onRefresh()
            }

            override fun onLoadMore() {
                super.onLoadMore()
            }

            override fun onRefreshEnd(data: List<*>?) {
                super.onRefreshEnd(data)
                LogUtil.e("空数据页", "刷新结束")
                if (data?.isEmpty() == false) {
                    emptyView.changeState(NetError())
                }
            }

            override fun onLoadMoreEnd(data: List<*>?) {
                super.onLoadMoreEnd(data)
                LogUtil.e("空数据页", "加载结束")
            }
        }
    }
}