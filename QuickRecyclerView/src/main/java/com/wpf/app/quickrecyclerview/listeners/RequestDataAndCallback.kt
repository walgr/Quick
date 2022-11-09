package com.wpf.app.quickrecyclerview.listeners

import android.view.View
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData
import com.wpf.app.quickutil.Callback

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
interface RequestAndCallback<Data : QuickItemData>: RequestAndCallbackWithView<Data, View> {

    /**
     * 接口请求
     */
    fun requestAndCallback(callback: Callback<Data>)

    override fun requestAndCallback(view: View, callback: Callback<Data>) {
        requestAndCallback(callback)
    }
}

interface RequestAndCallbackWithView<Data : QuickItemData, View>: RequestDataAndCallbackWithView<RequestData, Data, View> {

    /**
     * 接口请求
     */
    fun requestAndCallback(view: View, callback: Callback<Data>)

    override fun requestAndCallback(view: View, requestData: RequestData, callback: Callback<Data>) {
        requestAndCallback(view, callback)
    }
}

fun <Data : QuickItemData, View> request2View(
    callbackF: (callback: Callback<Data>) -> Unit
) = object : RequestAndCallback<Data> {

    override fun requestAndCallback(callback: Callback<Data>) {
        callbackF.invoke(callback)
    }
}

fun <Data : QuickItemData, View> request2ViewWithView(
    callbackF: (view: View, callback: Callback<Data>) -> Unit
) = object : RequestAndCallbackWithView<Data, View> {

    override fun requestAndCallback(view: View, callback: Callback<Data>) {
        callbackF.invoke(view, callback)
    }
}

interface RequestDataAndCallback<Request : RequestData, Data : QuickItemData>: RequestDataAndCallbackWithView<Request, Data, Any> {

    /**
     * 接口请求
     */
    fun requestAndCallback(requestData: Request, callback: Callback<Data>)

    override fun requestAndCallback(view: Any, requestData: Request, callback: Callback<Data>) {
        requestAndCallback(requestData, callback)
    }
}

interface RequestDataAndCallbackWithView<Request : RequestData, Data : QuickItemData, View> {

    /**
     * 接口请求
     */
    fun requestAndCallback(view: View, requestData: Request, callback: Callback<Data>)

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
    callbackF: (requestData: Request, callback: Callback<Data>) -> Unit
) = object : RequestDataAndCallback<Request, Data> {

    override fun requestAndCallback(requestData: Request, callback: Callback<Data>) {
        callbackF.invoke(requestData, callback)
    }
}

fun <Request : RequestData, Data : QuickItemData, View> requestData2ListWithView(
    callbackF: (view: View, requestData: Request, callback: Callback<Data>) -> Unit
) = object : RequestDataAndCallbackWithView<Request, Data, View> {

    override fun requestAndCallback(view: View, requestData: Request, callback: Callback<Data>) {
        callbackF.invoke(view, requestData, callback)
    }
}

fun <Data : QuickItemData> request2List(
    callbackF: (requestData: RequestData, callback: Callback<Data>) -> Unit
) = object : RequestDataAndCallback<RequestData, Data> {

    override fun requestAndCallback(requestData: RequestData, callback: Callback<Data>) {
        callbackF.invoke(requestData, callback)
    }
}

fun <Data : QuickItemData, View> request2ListWithView(
    callbackF: (view: View, requestData: RequestData, callback: Callback<Data>) -> Unit
) = object : RequestDataAndCallbackWithView<RequestData, Data, View> {

    override fun requestAndCallback(view: View, requestData: RequestData, callback: Callback<Data>) {
        callbackF.invoke(view, requestData, callback)
    }
}