package com.wpf.app.quick.demo

import android.app.Application
import android.view.Gravity
import android.view.View
import com.wpf.app.quick.demo.http.TestApi
import com.wpf.app.quick.demo.http.TestCommonCallAdapterFactory
import com.wpf.app.quick.demo.http.TestGsonConverterFactory
import com.wpf.app.quick.demo.http.TestNormalCallAdapterFactory
import com.wpf.app.quicknetwork.helper.OkHttpCreateHelper
import com.wpf.app.quicknetwork.helper.RetrofitCreateHelper
import com.wpf.app.quicknetwork.interceptor.LogInterceptor
import com.wpf.app.quickrecyclerview.constant.BRConstant
import com.wpf.app.quickutil.helper.activity
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.sp
import com.wpf.app.quickutil.helper.toColor
import com.wpf.app.quickutil.helper.toDrawable
import com.wpf.app.quickutil.init.QuickInit
import com.wpf.app.quickwidget.title.QuickTitleView
import com.wpf.app.quickwork.widget.theme.QuickDialogThemeBase
import com.wpf.app.quickwork.widget.theme.QuickTextThemeBase
import com.wpf.app.quickwork.widget.theme.QuickTitleThemeBase

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        QuickInit.init(this)
        //配置全局ViewBinding
        BRConstant.initByBR(BR::class.java)
        initTestRequest()

        QuickTextThemeBase.registerDefaultTheme {
            textSize = 14f.sp
            textColor = R.color.black.toColor()
            hintTextColor = R.color.grey.toColor()
        }
        QuickTitleThemeBase.registerDefaultTheme {
            height = 48.dp
            background = R.color.purple_700.toDrawable()
            contentGravity = QuickTitleView.CONTENT_GRAVITY_CENTER
            showBackIcon = true
            backIcon = com.wpf.app.quickwidget.R.drawable.baseline_arrow_back_ios_new_20_white
            titleBold = true
            titleSize = 20f.dp
            titleColor = R.color.white.toColor()
            subTitleBold = false
            subTitleSize = 14f.dp
            subTitleColor = R.color.white.toColor()
            showBackIcon = true
            showLine = false
            isLinearLayout = true
        }
        QuickDialogThemeBase.registerDefaultTheme {
            gravity = Gravity.BOTTOM
            animStyleRes = R.style.DialogBottomTopAnim
            minHeight = 200.dp
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