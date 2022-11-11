package com.wpf.app.quick.demo.viewmodel

import android.annotation.SuppressLint
import android.widget.TextView
import com.google.gson.Gson
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.RequestTestActivity
import com.wpf.app.quick.demo.http.request
import com.wpf.app.quick.activity.viewmodel.QuickViewModel
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quickbind.helper.binddatahelper.Request2View
import com.wpf.app.quickbind.interfaces.request2View
import com.wpf.app.quickutil.LogUtil

class RequestTestViewModel: QuickViewModel<RequestTestActivity>() {

    override fun onViewCreated(baseView: RequestTestActivity) {
        request2View.manualRequest()
    }

//    @SuppressLint("NonConstantResourceId", "StaticFieldLeak")
//    @BindView(R.id.info)
//    val info: TextView? = null
//

//
//    private fun r首页文章() {
//        request {
//            首页文章列表(0)
//        }.success {
//            val result = Gson().toJson(it?.data) ?: ""
//            LogUtil.e("接口返回成功$result")
//            info?.text = result
//        }.fail {
//            val result = Gson().toJson(it?.data) ?: ""
//            LogUtil.e("接口返回失败:${it?.errorMsg}$result")
//            info?.text = result
//        }
//    }

    //代替上面注释的逻辑
    @SuppressLint("NonConstantResourceId", "StaticFieldLeak")
    @BindData2View(id = R.id.info, helper = Request2View::class)
    val request2View = request2View { callback ->
        request {
            首页文章列表(0)
        }.success {
            callback.callback(it?.data)
        }
    }.isManual { true }.manualRequest()
}