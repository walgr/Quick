package com.wpf.app.quickrecyclerview.listeners

import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData
import com.wpf.app.quickutil.CallbackList

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */

interface Request2List<Request : RequestData, Data : QuickItemData>: Request2ListWithView<Request, Data, Any> {

    /**
     * 接口请求
     */
    fun requestAndCallback(requestData: Request, callback: CallbackList<Data>)

    override fun requestAndCallback(view: Any, requestData: Request, callback: CallbackList<Data>) {
        requestAndCallback(requestData, callback)
    }
}

interface Request2ListWithView<Request : RequestData, Data : QuickItemData, View> {

    /**
     * 接口请求
     */
    fun requestAndCallback(view: View, requestData: Request, callback: CallbackList<Data>)

    /**
     * 刷新结束
     * 返回true表示刷新结束后自己刷新adapter
     */
    fun refreshFinish(): Boolean { return false }

    /**
     * 加载结束
     * 返回true表示刷新结束后自己刷新adapter
     */
    fun loadMoreFinish(): Boolean { return false }
}

fun <Request : RequestData, Data : QuickItemData> requestData2List(
    callbackF: (requestData: Request, callback: CallbackList<Data>) -> Unit
) = object : Request2List<Request, Data> {

    override fun requestAndCallback(requestData: Request, callback: CallbackList<Data>) {
        callbackF.invoke(requestData, callback)
    }
}

fun <Request : RequestData, Data : QuickItemData, View> requestData2ListWithView(
    callbackF: (view: View, requestData: Request, callback: CallbackList<Data>) -> Unit
) = object : Request2ListWithView<Request, Data, View> {

    override fun requestAndCallback(view: View, requestData: Request, callback: CallbackList<Data>) {
        callbackF.invoke(view, requestData, callback)
    }
}

fun <Data : QuickItemData> request2List(
    callbackF: (requestData: RequestData, callback: CallbackList<Data>) -> Unit
) = object : Request2List<RequestData, Data> {

    override fun requestAndCallback(requestData: RequestData, callback: CallbackList<Data>) {
        callbackF.invoke(requestData, callback)
    }
}

fun <Data : QuickItemData, View> request2ListWithView(
    callbackF: (view: View, requestData: RequestData, callback: CallbackList<Data>) -> Unit
) = object : Request2ListWithView<RequestData, Data, View> {

    override fun requestAndCallback(view: View, requestData: RequestData, callback: CallbackList<Data>) {
        callbackF.invoke(view, requestData, callback)
    }
}