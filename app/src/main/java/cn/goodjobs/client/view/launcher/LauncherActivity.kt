package cn.goodjobs.client.view.launcher

import cn.goodjobs.client.R
import com.wpf.app.base.activity.BaseActivity
import com.gyf.immersionbar.ktx.immersionBar

/**
 * Created by 王朋飞 on 2022/4/2.
 *
 */
class LauncherActivity: BaseActivity(R.layout.activity_launcher) {

    override fun initView() {
        immersionBar {
            statusBarAlpha(0f)
            statusBarDarkFont(true)
        }
    }
}