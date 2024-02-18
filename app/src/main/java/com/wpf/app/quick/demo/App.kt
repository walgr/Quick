package com.wpf.app.quick.demo

import android.app.Application
import com.wpf.app.quickrecyclerview.constant.BRConstant
import com.wpf.app.quick.demo.http.TestApi
import com.wpf.app.quick.demo.http.TestCommonCallAdapterFactory
import com.wpf.app.quick.demo.http.TestGsonConverterFactory
import com.wpf.app.quick.demo.http.TestNormalCallAdapterFactory
import com.wpf.app.quicknetwork.helper.OkHttpCreateHelper
import com.wpf.app.quicknetwork.helper.RetrofitCreateHelper
import com.wpf.app.quicknetwork.interceptor.LogInterceptor
import com.wpf.app.quickutil.init.QuickInit

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //配置全局ViewBinding
        BRConstant.initByBR(BR::class.java)
        initTestRequest()
        QuickInit.init(this)
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