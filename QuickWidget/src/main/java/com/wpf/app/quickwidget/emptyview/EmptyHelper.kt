package com.wpf.app.quickwidget.emptyview

import com.wpf.app.quickrecyclerview.listeners.RefreshResult
import com.wpf.app.quickutil.helper.generic.nullDefault

object EmptyHelper {

    fun <T: BaseEmptyView> T.bind(
        view: RefreshResult,
        onRefreshCallback: ((curData: List<*>?) -> Unit)? = null,
        onRefreshEndCallback: ((data: List<*>?) -> Unit)? = null,
        onRefreshErrorCallback: ((e: Throwable) -> Unit)? = null,
        onLoadMoreCallback: ((curData: List<*>?) -> Unit)? = null,
        onLoadMoreEndCallback: ((data: List<*>?) -> Unit)? = null,
        onLoadMoreErrorCallback: ((e: Throwable) -> Unit)? = null,
    ): T {
        view.onRefresh {
            if (onRefreshCallback != null) {
                onRefreshCallback.invoke(it)
            } else {
                changeState(StateLoading.new(it?.size.nullDefault(0) == 0))
            }
        }
        view.onRefreshEnd {
            if (onRefreshEndCallback != null) {
                onRefreshEndCallback.invoke(it)
            } else {
                if (it.isNullOrEmpty()) {
                    changeState(StateEmptyData)
                } else {
                    changeState(StateNoError)
                }
            }
        }
        view.onRefreshError {
            if (onRefreshErrorCallback != null) {
                onRefreshErrorCallback.invoke(it)
            } else {
                changeState(StateNetError())
            }
        }
        view.onLoadMore {
            onLoadMoreCallback?.invoke(it)
        }
        view.onLoadMoreEnd {
            onLoadMoreEndCallback?.invoke(it)
        }
        view.onLoadMoreError {
            onLoadMoreErrorCallback?.invoke(it)
        }
        return this
    }
}