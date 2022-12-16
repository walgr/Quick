package com.wpf.app.quickrecyclerview.data

import androidx.annotation.CallSuper

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class RequestData @JvmOverloads constructor(
    open var page: Int = 0,
    open var pageSize: Int = 10
) {

    var isViewRefresh = true
    //刷新偏移量
    open var offset: Int = 0
    open fun refresh() {
        page = 0
        isViewRefresh = true
        offset = 0
    }

    open fun loadMore() {
        page++
        isViewRefresh = false
    }

    /**
     * 每次刷新或加载更多的数量
     */
    @CallSuper
    open fun loadDataSize(size: Int) {
        offset += size
    }
}