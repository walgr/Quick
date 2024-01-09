package com.wpf.app.quick.demo.fragment

import com.wpf.app.quick.activity.QuickBindingFragment
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.databinding.FragmentMainTestBinding

class MainTestFragment : QuickBindingFragment<FragmentMainTestBinding>(
    R.layout.fragment_main_test,
    titleName = "测试场"
)