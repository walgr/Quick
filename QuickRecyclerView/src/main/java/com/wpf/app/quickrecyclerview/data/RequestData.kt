package com.wpf.app.quickrecyclerview.data

import androidx.annotation.CallSuper

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class RequestData @JvmOverloads constructor(
    var page: Int = 0,
    var pageSize: Int = 10
) {
    private var firstPage = page

    var isViewRefresh = true
    //刷新偏移量
    open var offset: Int = 0
    open fun refresh(): RequestData {
        page = firstPage
        isViewRefresh = true
        offset = 0
        return this
    }

    open fun loadMore(): RequestData {
        page++
        isViewRefresh = false
        return this
    }

    /**
     * 每次刷新或加载更多的数量
     */
    @CallSuper
    open fun loadDataSize(size: Int) {
        offset += size
    }

    internal fun <T : RequestData> resetData(build: T.() -> Unit) {
        build.invoke(this as T)
        this.firstPage = this.page
    }
}