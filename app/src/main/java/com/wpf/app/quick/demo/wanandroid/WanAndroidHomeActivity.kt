package com.wpf.app.quick.demo.wanandroid

import com.wpf.app.quick.activity.QuickBindingActivity
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.databinding.ActivityWanandroidHomeBinding
import com.wpf.app.quick.helper.GotoThis
import com.wpf.app.quickrecyclerview.data.RequestData

class WanAndroidHomeActivity :
    QuickBindingActivity<ActivityWanandroidHomeBinding>(R.layout.activity_wanandroid_home),
    GotoThis {

    override fun setBindingVariable(viewDataBinding: ActivityWanandroidHomeBinding?) {
        super.setBindingVariable(viewDataBinding)
        viewBinding?.requestData = RequestData(1)
    }
}