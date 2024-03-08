package com.wpf.app.quick.demo

import android.app.Application
import android.graphics.Color
import android.view.View
import com.wpf.app.quickrecyclerview.constant.BRConstant
import com.wpf.app.quick.demo.http.TestApi
import com.wpf.app.quick.demo.http.TestCommonCallAdapterFactory
import com.wpf.app.quick.demo.http.TestGsonConverterFactory
import com.wpf.app.quick.demo.http.TestNormalCallAdapterFactory
import com.wpf.app.quicknetwork.helper.OkHttpCreateHelper
import com.wpf.app.quicknetwork.helper.RetrofitCreateHelper
import com.wpf.app.quicknetwork.interceptor.LogInterceptor
import com.wpf.app.quickutil.activity.activity
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.init.QuickInit
import com.wpf.app.quickwork.widget.QuickTitleView

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

        QuickTitleView.commonStyleBuilder {
            background = R.color.purple_700
            contentGravity = QuickTitleView.CONTENT_GRAVITY_START
            showBackIcon = true
            backIcon = com.wpf.app.quickwork.R.drawable.baseline_arrow_back_ios_new_20_white
            titleBold = true
            titleSize = 20.dp(it)
            titleColor = Color.WHITE
            subTitleBold = false
            subTitleSize = 14.dp(it)
            subTitleColor = Color.WHITE
            showBackIcon = true
            showLine = false
            isLinearLayout = true
            space = 16.dp(it)
        }
        QuickTitleView.setCommonClickListener(object : QuickTitleView.CommonClickListener {
            override fun onBackClick(view: View) {
                super.onBackClick(view)
                view.activity().finish()
            }
        })
    }

    private fun initTestRequest() {
        RetrofitCreateHelper.newInstance(
            "https://www.wanandroid.com",
            TestApi::class.java,
            arrayListOf(
                TestCommonCallAdapterFactory.create(),
                TestNormalCallAdapterFactory.create()
            ),
            converterFactoryList = arrayListOf(TestGsonConverterFactory.create()),
            OkHttpCreateHelper.newInstance(interceptorList = arrayListOf(LogInterceptor(this)))
        )
    }
}