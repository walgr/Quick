package com.wpf.app.quickrecyclerview.listeners

import androidx.annotation.CallSuper
import com.wpf.app.quicknetwork.base.BaseRequest
import com.wpf.app.quicknetwork.base.BaseResponseI
import com.wpf.app.quicknetwork.utils.RequestCallback
import com.wpf.app.quickrecyclerview.QuickRefreshRecyclerView
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
interface Request2List<Request : RequestData, Data : QuickItemData> :
    Request2ListWithView<Request, Data, QuickRefreshRecyclerView> {

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

    override fun requestAndCallback(
        view: QuickRefreshRecyclerView,
        requestData: Request,
        callback: RequestCallback<Data>,
    ) {
        super.requestAndCallback(view, requestData, callback)
        requestAndCallback(requestData, callback)
    }
}

interface Request2ListWithView<Request : RequestData, Data : QuickItemData, View> {

    var request2List: Request2ListWithView<Request, Data, View>?
    var view: View?
    var requestData: Request?
    var baseRequest: BaseRequest<out BaseResponseI<out Any, out Any>, out Any>?
    var refreshCallback: RequestCallback<Data>?
    var loadMoreCallback: RequestCallback<Data>?
    var refreshRun: ((hasMore: Boolean) -> Boolean)?
    var loadMoreRun: ((hasMore: Boolean) -> Boolean)?

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

    fun initRequestData(build: Request.() -> Unit): Request2ListWithView<Request, Data, View> {
        requestData?.resetData(build)
        return this
    }

    /**
     * 刷新结束
     * 返回true表示刷新结束后自己刷新adapter
     */
    fun refreshFinish(hasMore: Boolean = true): Boolean {
        refreshRun?.let {
            return it(hasMore)
        }
        return false
    }

    fun refreshFinish(run: (hasMore: Boolean) -> Boolean): Request2ListWithView<Request, Data, View> {
        this.refreshRun = run
        return this
    }

    /**
     * 加载结束
     * 返回true表示刷新结束后自己刷新adapter
     */
    fun loadMoreFinish(hasMore: Boolean = true): Boolean {
        loadMoreRun?.let {
            return it(hasMore)
        }
        return false
    }

    fun loadMoreFinish(run: (hasMore: Boolean) -> Boolean): Request2ListWithView<Request, Data, View> {
        this.loadMoreRun = run
        return this
    }

    /**
     * 按照最后的请求手动再次调用1次
     */
    @Suppress("unused")
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

    @Suppress("unused")
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
    @Suppress("unused")
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

@Suppress("unused")
inline fun <reified Request : RequestData, reified Data : QuickItemData> requestData2List(
    noinline callbackF: (requestData: Request, callback: RequestCallback<Data>) -> BaseRequest<out BaseResponseI<out Any, out Any>, out Any>,
) = object : Request2List<Request, Data> {
    override var request2List: Request2ListWithView<Request, Data, QuickRefreshRecyclerView>? = this
    override var view: QuickRefreshRecyclerView? = null
    override var requestData: Request? = null
    override var baseRequest: BaseRequest<out BaseResponseI<out Any, out Any>, out Any>? = null
    override var refreshCallback: RequestCallback<Data>? = null
    override var loadMoreCallback: RequestCallback<Data>? = null
    override var refreshRun: ((hasMore: Boolean) -> Boolean)? = null
    override var loadMoreRun: ((hasMore: Boolean) -> Boolean)? = null

    init {
        requestData = Request::class.java.newInstance()
    }

    override fun requestAndCallback(requestData: Request, callback: RequestCallback<Data>) {
        super.requestAndCallback(requestData, callback)
        baseRequest = callbackF.invoke(requestData, callback).fail {
            if (requestData.isViewRefresh) {
                super.refreshFinish(false)
            } else {
                super.loadMoreFinish(false)
            }
        }.error {
            if (requestData.isViewRefresh) {
                super.refreshFinish(false)
            } else {
                super.loadMoreFinish(false)
            }
        }
    }
}

@Suppress("unused")
inline fun <reified Request : RequestData, reified Data : QuickItemData, View> requestData2ListWithView(
    noinline callbackF: (requestData: Request, callback: RequestCallback<Data>, view: View) -> BaseRequest<out BaseResponseI<out Any, out Any>, out Any>,
) = object : Request2ListWithView<Request, Data, View> {
    override var request2List: Request2ListWithView<Request, Data, View>? = this
    override var view: View? = null
    override var requestData: Request? = null
    override var baseRequest: BaseRequest<out BaseResponseI<out Any, out Any>, out Any>? = null
    override var refreshCallback: RequestCallback<Data>? = null
    override var loadMoreCallback: RequestCallback<Data>? = null
    override var refreshRun: ((hasMore: Boolean) -> Boolean)? = null
    override var loadMoreRun: ((hasMore: Boolean) -> Boolean)? = null

    init {
        requestData = Request::class.java.newInstance()
    }

    override fun requestAndCallback(
        view: View,
        requestData: Request,
        callback: RequestCallback<Data>,
    ) {
        super.requestAndCallback(view, requestData, callback)
        baseRequest = callbackF.invoke(requestData, callback, view)
    }
}

@Suppress("unused")
fun <Data : QuickItemData> request2List(
    callbackF: (requestData: RequestData, callback: RequestCallback<Data>) -> BaseRequest<out BaseResponseI<out Any, out Any>, out Any>,
) = object : Request2List<RequestData, Data> {
    override var request2List: Request2ListWithView<RequestData, Data, QuickRefreshRecyclerView>? =
        this
    override var view: QuickRefreshRecyclerView? = null
    override var requestData: RequestData? = null
    override var baseRequest: BaseRequest<out BaseResponseI<out Any, out Any>, out Any>? = null
    override var refreshCallback: RequestCallback<Data>? = null
    override var loadMoreCallback: RequestCallback<Data>? = null
    override var refreshRun: ((hasMore: Boolean) -> Boolean)? = null
    override var loadMoreRun: ((hasMore: Boolean) -> Boolean)? = null

    init {
        requestData = RequestData()
    }

    override fun requestAndCallback(requestData: RequestData, callback: RequestCallback<Data>) {
        super.requestAndCallback(requestData, callback)
        baseRequest = callbackF.invoke(requestData, callback)
    }
}


@Suppress("unused")
fun <Data : QuickItemData, View> request2ListWithView(
    callbackF: (view: View, requestData: RequestData, callback: RequestCallback<Data>) -> BaseRequest<out BaseResponseI<out Any, out Any>, out Any>,
) = object : Request2ListWithView<RequestData, Data, View> {
    override var request2List: Request2ListWithView<RequestData, Data, View>? = this
    override var view: View? = null
    override var requestData: RequestData? = null
    override var baseRequest: BaseRequest<out BaseResponseI<out Any, out Any>, out Any>? = null
    override var refreshCallback: RequestCallback<Data>? = null
    override var loadMoreCallback: RequestCallback<Data>? = null
    override var refreshRun: ((hasMore: Boolean) -> Boolean)? = null
    override var loadMoreRun: ((hasMore: Boolean) -> Boolean)? = null

    init {
        requestData = RequestData()
    }

    override fun requestAndCallback(
        view: View,
        requestData: RequestData,
        callback: RequestCallback<Data>,
    ) {
        super.requestAndCallback(view, requestData, callback)
        baseRequest = callbackF.invoke(view, requestData, callback)
    }
}