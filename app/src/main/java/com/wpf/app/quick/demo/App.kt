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
import com.wpf.app.quickutil.helper.dpF
import com.wpf.app.quickutil.helper.toColor
import com.wpf.app.quickutil.helper.toDrawable
import com.wpf.app.quickutil.init.QuickInit
import com.wpf.app.quickwidget.title.QuickTitleAttrs
import com.wpf.app.quickwidget.title.QuickTitleView
import com.wpf.app.quickwork.widget.theme.QuickDialogTheme
import com.wpf.app.quickwork.widget.theme.QuickDialogThemeBase
import com.wpf.app.quickwork.widget.theme.QuickTextTheme
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

        QuickTextThemeBase.defaultTheme = QuickTextTheme().apply {
            textSize = 14.dpF()
            textColor = R.color.black.toColor()
            hintTextColor = R.color.grey.toColor()
        }
        QuickTitleThemeBase.defaultTheme = QuickTitleAttrs().apply {
            height = 48.dp()
            background = R.color.purple_700.toDrawable()
            contentGravity = QuickTitleView.CONTENT_GRAVITY_CENTER
            showBackIcon = true
            backIcon = com.wpf.app.quickwidget.R.drawable.baseline_arrow_back_ios_new_20_white
            titleBold = true
            titleSize = 20.dpF()
            titleColor = R.color.white.toColor()
            subTitleBold = false
            subTitleSize = 14.dpF()
            subTitleColor = R.color.white.toColor()
            showBackIcon = true
            showLine = false
            isLinearLayout = true
            space = 16.dp()
        }
        QuickDialogThemeBase.defaultTheme = QuickDialogTheme().apply {
            gravity = Gravity.BOTTOM
            animRes = R.anim.anim_bottom_top
            minHeight = 200.dp()
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