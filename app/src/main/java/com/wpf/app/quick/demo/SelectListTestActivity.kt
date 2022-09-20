package com.wpf.app.quick.demo

import com.wpf.app.quick.activity.QuickViewModelBindingActivity
import com.wpf.app.quick.demo.databinding.ActivitySelectTestBinding
import com.wpf.app.quick.demo.viewmodel.SelectListViewModel

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class SelectListTestActivity :
    QuickViewModelBindingActivity<SelectListViewModel, ActivitySelectTestBinding>(
        R.layout.activity_select_test,
        titleName = "选择筛选页"
    )