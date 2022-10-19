package com.wpf.app.quickrecyclerview.data

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class RequestData(
    open var page: Int = 0,
    open var pageSize: Int = 10
) {
    open fun refresh() {
        page = 0
    }

    open fun loadMore() {
        page++
    }
}