package com.wpf.app.quick.demo

import android.app.Application
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
import com.wpf.app.quickutil.helper.dpF
import com.wpf.app.quickutil.helper.toColor
import com.wpf.app.quickutil.init.QuickInit
import com.wpf.app.quickwidget.title.QuickTitleView
import com.wpf.app.quickwork.widget.QuickThemeTextView
import com.wpf.app.quickwork.widget.QuickThemeTitle

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

        QuickThemeTextView.setCommonTheme(this) {
            textSize = 14.dpF(it)
            textColor = R.color.black.toColor(it)
            hintTextColor = R.color.grey.toColor(it)
        }
        QuickThemeTitle.childThemeBuilder(this) {
            titleSize = 16.dpF(it)
            titleColor = R.color.white.toColor(it)
            titleBold = false

            imgWidth = 24.dp(it)
            imgHeight = 24.dp(it)

            space = 4.dp(it)
        }
        QuickThemeTitle.commonThemeBuilder(this) {
            background = R.color.purple_700
            contentGravity = QuickTitleView.CONTENT_GRAVITY_CENTER
            showBackIcon = true
            backIcon = com.wpf.app.quickwidget.R.drawable.baseline_arrow_back_ios_new_20_white
            titleBold = true
            titleSize = 20.dpF(it)
            titleColor = R.color.white.toColor(it)
            subTitleBold = false
            subTitleSize = 14.dpF(it)
            subTitleColor = R.color.white.toColor(it)
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