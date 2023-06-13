package com.wpf.app.quick.demo.wanandroid

import com.wpf.app.quick.activity.QuickBindingActivity
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.databinding.ActivityWanandroidHomeBinding
import com.wpf.app.quick.helper.GotoThis

class WanAndroidHomeActivity :
    QuickBindingActivity<ActivityWanandroidHomeBinding>(R.layout.activity_wanandroid_home),
    GotoThis {

    override fun initView() {
        super.initView()
    }

    override fun gotoActivity(): Class<*> {
        return WanAndroidHomeActivity::class.java
    }
}