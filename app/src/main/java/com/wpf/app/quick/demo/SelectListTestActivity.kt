package com.wpf.app.quick.demo

import com.wpf.app.quick.ability.QuickAbilityActivity
import com.wpf.app.quick.ability.modelBinding
import com.wpf.app.quick.ability.with
import com.wpf.app.quick.activity.QuickVBActivity
import com.wpf.app.quick.demo.databinding.ActivityRequestTestBinding
import com.wpf.app.quick.demo.databinding.ActivitySelectTestBinding
import com.wpf.app.quick.demo.viewmodel.RequestTestModel
import com.wpf.app.quick.demo.viewmodel.SelectListModel
import com.wpf.app.quickwork.activity.contentWithTitle

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class SelectListTestActivity : QuickAbilityActivity(
    contentWithTitle(
        R.layout.activity_select_test, titleName = "选择筛选页"
    ).with(modelBinding<SelectListModel, ActivitySelectTestBinding>())
)