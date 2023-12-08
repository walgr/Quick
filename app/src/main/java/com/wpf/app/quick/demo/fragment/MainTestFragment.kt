package com.wpf.app.quick.demo.fragment

import com.wpf.app.quick.activity.QuickVBFragment
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.databinding.FragmentMainTestBinding

class MainTestFragment: QuickVBFragment<MainTestVM, FragmentMainTestBinding>(R.layout.fragment_main_test, "测试场") {
    override fun initView(viewDataBinding: FragmentMainTestBinding?) {
    }
}