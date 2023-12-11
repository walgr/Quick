package com.wpf.app.quick.demo

import android.app.Application
import com.wpf.app.quickrecyclerview.constant.BRConstant
import com.wpf.app.quick.demo.http.TestApi
import com.wpf.app.quick.demo.http.TestGsonConverterFactory
import com.wpf.app.quick.demo.http.call.TestCommonCallAdapterFactory
import com.wpf.app.quick.demo.http.call.TestNormalCallAdapterFactory
import com.wpf.app.quicknetwork.helper.OkHttpCreateHelper
import com.wpf.app.quicknetwork.helper.RetrofitCreateHelper
import com.wpf.app.quicknetwork.interceptor.LogInterceptor

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //配置全局ViewBinding
        BRConstant.viewModel = BR.viewModel
        BRConstant.data = BR.data
        BRConstant.adapter = BR.adapter
        BRConstant.position = BR.position
        initTestRequest()
    }

    private fun initTestRequest() {
        RetrofitCreateHelper.newInstance(
            "https://www.wanandroid.com",
            TestApi::class.java,
            arrayListOf(TestCommonCallAdapterFactory.create(), TestNormalCallAdapterFactory.create()),
            converterFactoryList = arrayListOf(TestGsonConverterFactory.create()),
            OkHttpCreateHelper.newInstance(interceptorList = arrayListOf(LogInterceptor(this)))
        )
    }
}