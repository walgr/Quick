package com.wpf.app.quickrecyclerview.listeners

import androidx.annotation.CallSuper
import com.wpf.app.quicknetwork.utils.RequestCallback
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData
import java.lang.reflect.ParameterizedType

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */

interface Request2List<Request : RequestData, Data : QuickItemData> : Request2ListWithView<Request, Data, Any> {

    /**
     * 接口请求
     */
    @CallSuper
    fun requestAndCallback(requestData: Request, callback: RequestCallback<Data>) {
        this.requestData = requestData
        if (requestData.isViewRefresh) {
            this.refreshCallback = callback
        } else {
            this.loadMoreCallback = callback
        }
    }

    override fun requestAndCallback(view: Any, requestData: Request, callback: RequestCallback<Data>) {
        super.requestAndCallback(view, requestData, callback)
        requestAndCallback(requestData, callback)
    }
}

interface Request2ListWithView<Request : RequestData, Data : QuickItemData, View> {

    var request2List: Request2ListWithView<Request, Data, View>?
    var view: View?
    var requestData: Request?
    var refreshCallback: RequestCallback<Data>?
    var loadMoreCallback: RequestCallback<Data>?
    var refreshRun: ((Boolean) -> Boolean)?
    var loadMoreRun: ((Boolean) -> Boolean)?

    /**
     * 接口请求
     */
    @CallSuper
    fun requestAndCallback(view: View, requestData: Request, callback: RequestCallback<Data>) {
        this.view = view
        this.requestData = requestData
        if (requestData.isViewRefresh) {
            this.refreshCallback = callback
        } else {
            this.loadMoreCallback = callback
        }
    }

    /**
     * 刷新结束
     * 返回true表示刷新结束后自己刷新adapter
     */
    fun refreshFinish(hasMore: Boolean): Boolean {
        refreshRun?.let {
            return it(hasMore)
        }
        return false
    }

    fun refreshFinish(run: (Boolean) -> Boolean): Request2ListWithView<Request, Data, View> {
        this.refreshRun = run
        return this
    }

    /**
     * 加载结束
     * 返回true表示刷新结束后自己刷新adapter
     */
    fun loadMoreFinish(hasMore: Boolean): Boolean {
        loadMoreRun?.let {
            return it(hasMore)
        }
        return false
    }

    fun loadMoreFinish(run: (Boolean) -> Boolean): Request2ListWithView<Request, Data, View> {
        this.loadMoreRun = run
        return this
    }

    /**
     * 按照最后的请求手动再次调用1次
     */
    @CallSuper
    fun manualRequest(): Request2ListWithView<Request, Data, View> {
        if (view != null && requestData != null) {
            if (requestData!!.isViewRefresh) {
                refreshCallback?.let {
                    request2List?.requestAndCallback(view!!, requestData!!, it)
                }
            } else {
                loadMoreCallback?.let {
                    request2List?.requestAndCallback(view!!, requestData!!, it)
                }
            }
        }
        return this
    }

    @CallSuper
    fun manualRequest(requestData: Request): Request2ListWithView<Request, Data, View> {
        if (requestData.isViewRefresh) {
            refreshCallback?.let {
                request2List?.requestAndCallback(view!!, requestData, it)
            }
        } else {
            loadMoreCallback?.let {
                request2List?.requestAndCallback(view!!, requestData, it)
            }
        }
        return this
    }

    /**
     * 按照新请求手动再次调用1次
     */
    @CallSuper
    fun manualRequest(view: View, requestData: Request): Request2ListWithView<Request, Data, View> {
        if (requestData.isViewRefresh) {
            refreshCallback?.let {
                request2List?.requestAndCallback(view, requestData, it)
            }
        } else {
            loadMoreCallback?.let {
                request2List?.requestAndCallback(view, requestData, it)
            }
        }
        return this
    }
}

fun <Request : RequestData, Data : QuickItemData> requestData2List(
    callbackF: (requestData: Request, callback: RequestCallback<Data>) -> Unit
) = object : Request2List<Request, Data> {
    override var request2List: Request2ListWithView<Request, Data, Any>? = this
    override var view: Any? = null
    override var requestData: Request? = null
    override var refreshCallback: RequestCallback<Data>? = null
    override var loadMoreCallback: RequestCallback<Data>? = null
    override var refreshRun: ((Boolean) -> Boolean)? = null
    override var loadMoreRun: ((Boolean) -> Boolean)? = null

    init {
        if ((callbackF.javaClass.genericInterfaces[0] as ParameterizedType).actualTypeArguments[0] is Class<*>) {
            requestData =
                ((callbackF.javaClass.genericInterfaces[0] as ParameterizedType).actualTypeArguments[0] as Class<*>).getDeclaredConstructor()
                    .newInstance() as Request
        }
    }

    override fun requestAndCallback(requestData: Request, callback: RequestCallback<Data>) {
        super.requestAndCallback(requestData, callback)
        callbackF.invoke(requestData, callback)
    }
}

fun <Request : RequestData, Data : QuickItemData, View> requestData2ListWithView(
    callbackF: (view: View, requestData: Request, callback: RequestCallback<Data>) -> Unit
) = object : Request2ListWithView<Request, Data, View> {
    override var request2List: Request2ListWithView<Request, Data, View>? = this
    override var view: View? = null
    override var requestData: Request? = null
    override var refreshCallback: RequestCallback<Data>? = null
    override var loadMoreCallback: RequestCallback<Data>? = null
    override var refreshRun: ((Boolean) -> Boolean)? = null
    override var loadMoreRun: ((Boolean) -> Boolean)? = null

    override fun requestAndCallback(view: View, requestData: Request, callback: RequestCallback<Data>) {
        super.requestAndCallback(view, requestData, callback)
        callbackF.invoke(view, requestData, callback)
    }
}

fun <Data : QuickItemData> request2List(
    callbackF: (requestData: RequestData, callback: RequestCallback<Data>) -> Unit
) = object : Request2List<RequestData, Data> {
    override var request2List: Request2ListWithView<RequestData, Data, Any>? = this
    override var view: Any? = null
    override var requestData: RequestData? = null
    override var refreshCallback: RequestCallback<Data>? = null
    override var loadMoreCallback: RequestCallback<Data>? = null
    override var refreshRun: ((Boolean) -> Boolean)? = null
    override var loadMoreRun: ((Boolean) -> Boolean)? = null

    override fun requestAndCallback(requestData: RequestData, callback: RequestCallback<Data>) {
        super.requestAndCallback(requestData, callback)
        callbackF.invoke(requestData, callback)
    }
}

fun <Data : QuickItemData, View> request2ListWithView(
    callbackF: (view: View, requestData: RequestData, callback: RequestCallback<Data>) -> Unit
) = object : Request2ListWithView<RequestData, Data, View> {
    override var request2List: Request2ListWithView<RequestData, Data, View>? = this
    override var view: View? = null
    override var requestData: RequestData? = null
    override var refreshCallback: RequestCallback<Data>? = null
    override var loadMoreCallback: RequestCallback<Data>? = null
    override var refreshRun: ((Boolean) -> Boolean)? = null
    override var loadMoreRun: ((Boolean) -> Boolean)? = null

    override fun requestAndCallback(view: View, requestData: RequestData, callback: RequestCallback<Data>) {
        super.requestAndCallback(view, requestData, callback)
        callbackF.invoke(view, requestData, callback)
    }
}