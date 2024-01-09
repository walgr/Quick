package com.wpf.app.quick.demo

import com.wpf.app.quick.activity.QuickVBActivity
import com.wpf.app.quick.annotations.getclass.GetClass
import com.wpf.app.quick.demo.databinding.ActivityRequestTestBinding
import com.wpf.app.quick.demo.viewmodel.RequestTestModel

@GetClass
class RequestTestActivity : QuickVBActivity<RequestTestModel, ActivityRequestTestBinding>(
    R.layout.activity_request_test,
    titleName = "接口测试页"
)