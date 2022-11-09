package com.wpf.app.quickbind.interfaces

import android.view.View
import com.wpf.app.quickbind.bindview.QuickRequestData
import com.wpf.app.quickutil.Callback


interface Request2View<Data : QuickRequestData>: Request2ViewWithView<Data, View> {

    /**
     * 接口请求
     */
    fun requestAndCallback(callback: Callback<Data>)

    override fun requestAndCallback(view: View, callback: Callback<Data>) {
        requestAndCallback(callback)
    }
}

interface Request2ViewWithView<Data : QuickRequestData, View> {

    /**
     * 接口请求
     */
    fun requestAndCallback(view: View, callback: Callback<Data>)
}

fun <Data : QuickRequestData> request2View(
    callbackF: (callback: Callback<Data>) -> Unit
) = object : Request2View<Data> {

    override fun requestAndCallback(callback: Callback<Data>) {
        callbackF.invoke(callback)
    }
}

fun <Data : QuickRequestData, View> request2ViewWithView(
    callbackF: (view: View, callback: Callback<Data>) -> Unit
) = object : Request2ViewWithView<Data, View> {

    override fun requestAndCallback(view: View, callback: Callback<Data>) {
        callbackF.invoke(view, callback)
    }
}