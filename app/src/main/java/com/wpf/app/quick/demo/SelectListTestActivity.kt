package com.wpf.app.quick.demo

import com.wpf.app.quick.activity.QuickVBActivity
import com.wpf.app.quick.demo.databinding.ActivitySelectTestBinding
import com.wpf.app.quick.demo.viewmodel.SelectListModel

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class SelectListTestActivity :
    QuickVBActivity<SelectListModel, ActivitySelectTestBinding>(
        R.layout.activity_select_test,
        titleName = "选择筛选页"
    )