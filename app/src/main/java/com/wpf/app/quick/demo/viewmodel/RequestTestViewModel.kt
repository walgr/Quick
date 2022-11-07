package com.wpf.app.quick.demo.viewmodel

import android.annotation.SuppressLint
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.RequestTestActivity
import com.wpf.app.quick.demo.http.request
import com.wpf.app.quick.activity.viewmodel.QuickViewModel
import com.wpf.app.quicknetwork.helper.GsonHelper
import com.wpf.app.quickutil.LogUtil

class RequestTestViewModel: QuickViewModel<RequestTestActivity>() {

    @SuppressLint("NonConstantResourceId", "StaticFieldLeak")
    @BindView(R.id.info)
    val info: TextView? = null

    override fun onViewCreated(baseView: RequestTestActivity) {
        r首页文章()
    }

    private fun r首页文章() {
        request {
            首页文章列表(0)
        }.success {
            val result = Gson().toJson(it?.data) ?: ""
            LogUtil.e("接口返回成功$result")
            info?.text = result
        }.fail {
            val result = Gson().toJson(it?.data) ?: ""
            LogUtil.e("接口返回失败:${it?.errorMsg}$result")
            info?.text = result
        }
    }
}