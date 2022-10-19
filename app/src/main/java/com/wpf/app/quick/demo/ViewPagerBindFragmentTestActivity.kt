package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import com.wpf.app.quick.activity.QuickActivity
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quickbind.annotations.BindFragment
import com.wpf.app.quickbind.viewpager.QuickViewPager

/**
 * Created by 王朋飞 on 2022/8/5.
 *
 */
class ViewPagerBindFragmentTestActivity: QuickActivity(R.layout.activity_test_viewpager) {

    @SuppressLint("NonConstantResourceId")
    @BindFragment(fragment = TestFragment::class, withState = false, defaultSize = 30)
    @BindView(R.id.viewPager)
    var viewPager: QuickViewPager? = null

    override fun initView() {

    }
}