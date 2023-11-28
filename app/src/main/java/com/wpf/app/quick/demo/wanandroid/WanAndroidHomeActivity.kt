package com.wpf.app.quick.demo.wanandroid

import com.wpf.app.quick.activity.QuickBindingActivity
import com.wpf.app.quick.annotations.GetClass
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.databinding.ActivityWanandroidHomeBinding
import com.wpf.app.quickrecyclerview.utils.QuickStickyView
import com.wpf.app.quickrecyclerview.utils.StickyItemDecoration

@GetClass
class WanAndroidHomeActivity :
    QuickBindingActivity<ActivityWanandroidHomeBinding>(R.layout.activity_wanandroid_home) {

    override fun initView(viewDataBinding: ActivityWanandroidHomeBinding?) {
        super.initView(viewDataBinding)
//        viewDataBinding?.list?.addItemDecoration(StickyItemDecoration(QuickStickyView()))
    }
}