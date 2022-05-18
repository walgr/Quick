package com.wpf.app.quick

import android.app.Application
import com.wpf.app.quick.base.constant.BRConstant

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        BRConstant.viewModel = BR.viewModel
        BRConstant.data = BR.data
        BRConstant.adapter = BR.adapter
        BRConstant.position = BR.position
    }
}