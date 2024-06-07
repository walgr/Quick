package com.wpf.app.quickrecyclerview.interfaces

import androidx.annotation.CallSuper
import com.wpf.app.base.callback.Callback
import com.wpf.app.quickrecyclerview.data.QuickRequestData


interface Request2View<Data : QuickRequestData> : Request2ViewWithView<Data, Any> {

    /**
     * 接口请求
     */
    @CallSuper
    fun requestAndCallback(callback: com.wpf.app.base.callback.Callback<Data>) {
        this.callback = callback
    }

    override fun requestAndCallback(view: Any, callback: com.wpf.app.base.callback.Callback<Data>) {
        super.requestAndCallback(view, callback)
        requestAndCallback(callback)
    }
}

interface Request2ViewWithView<Data : QuickRequestData, View> {
    var request2List: Request2ViewWithView<Data, View>?
    var view: View?
    var callback: Callback<Data>?
    var isAuto: Boolean               //是否自动请求

    /**
     * 接口请求
     */
    @CallSuper
    fun requestAndCallback(view: View, callback: Callback<Data>) {
        this.view = view
        this.callback = callback
    }

    /**
     * 返回是否手动触发
     */
    fun isAutoFun(auto: (() -> Boolean)?): Boolean {
        auto?.let {
            isAuto = it()
            return isAuto
        }
        return false
    }

    fun autoRequest(auto: (() -> Boolean)?): Request2ViewWithView<Data, View> {
        isAuto = isAutoFun(auto)
        return this
    }

    /**
     * 再次调用1次requestAndCallback
     */
    @CallSuper
    fun manualRequest(): Request2ViewWithView<Data, View> {
        if (view != null) {
            callback?.let {
                request2List?.requestAndCallback(view!!, it)
            }
        }
        return this
    }
}

fun <Data : QuickRequestData> request2View(
    callbackF: (callback: Callback<Data>) -> Unit
) = object : Request2View<Data> {
    override var request2List: Request2ViewWithView<Data, Any>? = this
    override var view: Any? = null
    override var callback: Callback<Data>? = null
    override var isAuto: Boolean = false

    override fun requestAndCallback(callback: Callback<Data>) {
        super.requestAndCallback(callback)
        callbackF.invoke(callback)
    }
}

fun <Data : QuickRequestData, View> request2ViewWithView(
    callbackF: (view: View, callback: Callback<Data>) -> Unit
) = object : Request2ViewWithView<Data, View> {
    override var request2List: Request2ViewWithView<Data, View>? = this
    override var view: View? = null
    override var callback: Callback<Data>? = null
    override var isAuto: Boolean = false

    override fun requestAndCallback(view: View, callback: Callback<Data>) {
        super.requestAndCallback(view, callback)
        callbackF.invoke(view, callback)
    }
}