package com.wpf.app.quickrecyclerview.listeners

import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData
import com.wpf.app.quickutil.Callback

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
interface DataChangeOnListener<Request : RequestData, Data : QuickItemData> {
    /**
     * 下拉刷新
     */
    fun onRefresh(requestData: Request, callback: Callback<Data>)

    /**
     * 上拉加载
     */
    fun onLoadMore(requestData: Request, callback: Callback<Data>)

    /**
     * 刷新结束
     */
    fun refreshFinish() {}

    /**
     * 加载结束
     */
    fun loadMoreFinish() {}
}