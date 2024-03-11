package com.wpf.app.quick.demo

import com.wpf.app.quick.ability.QuickAbilityActivity
import com.wpf.app.quick.ability.modelBinding
import com.wpf.app.quick.ability.viewModel
import com.wpf.app.quick.ability.with
import com.wpf.app.quick.activity.QuickVBActivity
import com.wpf.app.quick.annotations.getclass.GetClass
import com.wpf.app.quick.demo.databinding.ActivityRequestTestBinding
import com.wpf.app.quick.demo.viewmodel.RequestTestModel
import com.wpf.app.quickwork.activity.contentWithTitle

@GetClass
class RequestTestActivity : QuickAbilityActivity(
    contentWithTitle(
        R.layout.activity_request_test, titleName = "接口测试页"
    ).with(modelBinding<RequestTestModel, ActivityRequestTestBinding>())
)