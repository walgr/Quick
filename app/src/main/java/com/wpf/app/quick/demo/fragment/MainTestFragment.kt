package com.wpf.app.quick.demo.fragment

import com.wpf.app.quick.activity.QuickVBFragment
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.databinding.FragmentMainTestBinding

class MainTestFragment : QuickVBFragment<MainTestVM, FragmentMainTestBinding>(
    R.layout.fragment_main_test,
    titleName = "测试场"
) {
    override fun initView(view: FragmentMainTestBinding?) {
        view?.shadow1?.postDelayed({
            view.shadow1.text = "2"
        }, 3000)
    }
}