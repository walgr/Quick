package com.wpf.app.quick.demo

import android.app.Application
import com.wpf.app.quick.constant.BRConstant

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        BRConstant.viewModel = BR.viewModel
        BRConstant.data = BR.data
        BRConstant.adapter = BR.adapter
        BRConstant.position = BR.position
    }
}