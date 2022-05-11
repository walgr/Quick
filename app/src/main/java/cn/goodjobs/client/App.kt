package cn.goodjobs.client

import android.app.Application
import com.wpf.app.base.constant.BRConstant

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        BRConstant.viewModel = BR.viewModel
    }
}