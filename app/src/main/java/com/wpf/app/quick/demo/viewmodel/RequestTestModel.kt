package com.wpf.app.quick.demo.viewmodel

import android.annotation.SuppressLint
import com.wpf.app.quick.activity.viewmodel.QuickVBModel
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.http.request
import com.wpf.app.quick.annotations.bind.BindData2View
import com.wpf.app.quick.demo.databinding.ActivityRequestTestBinding
import com.wpf.app.quickrecyclerview.bind.Request2View
import com.wpf.app.quickrecyclerview.interfaces.request2View
import com.wpf.app.quickutil.helper.postDelay
import com.wpf.app.quickutil.log.LogUtil

class RequestTestModel : QuickVBModel<ActivityRequestTestBinding>() {

    val requestParams = mutableMapOf("page" to 0, "pageSize" to 10)
    private var page = 0

    @SuppressLint("NonConstantResourceId", "StaticFieldLeak")
    @BindData2View(id = R.id.info, helper = Request2View::class)
    val info = request2View { callback ->
        request {
            首页文章列表(page)
        }.success {
            callback.backData(it?.data)
        }.fail {
            LogUtil.e("接口错误：${it?.errorI}")
        }
    }.autoRequest { false }

    override fun onBindingCreated(view: ActivityRequestTestBinding) {
        //手动请求
        view.info.postDelay(1000) {
            page = 1
            info.manualRequest()
        }
    }
}