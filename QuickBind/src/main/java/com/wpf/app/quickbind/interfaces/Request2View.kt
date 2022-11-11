package com.wpf.app.quickbind.interfaces

import androidx.annotation.CallSuper
import com.wpf.app.quickbind.bindview.QuickRequestData
import com.wpf.app.quickutil.Callback


interface Request2View<Data : QuickRequestData> : Request2ViewWithView<Data, Any> {

    /**
     * 接口请求
     */
    @CallSuper
    fun requestAndCallback(callback: Callback<Data>) {
        this.callback = callback
    }

    override fun requestAndCallback(view: Any, callback: Callback<Data>) {
        super.requestAndCallback(view, callback)
        requestAndCallback(callback)
    }
}

interface Request2ViewWithView<Data : QuickRequestData, View> {
    var request2List: Request2ViewWithView<Data, View>?
    var view: View?
    var callback: Callback<Data>?
    var isManual: Boolean

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
    fun isManualFun(manual: (() -> Boolean)?): Boolean {
        manual?.let {
            isManual = it()
            return isManual
        }
        return false
    }

    fun isManual(manual: (() -> Boolean)?): Request2ViewWithView<Data, View> {
        isManual = isManualFun(manual)
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
    override var isManual: Boolean = false

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
    override var isManual: Boolean = false

    override fun requestAndCallback(view: View, callback: Callback<Data>) {
        super.requestAndCallback(view, callback)
        callbackF.invoke(view, callback)
    }
}