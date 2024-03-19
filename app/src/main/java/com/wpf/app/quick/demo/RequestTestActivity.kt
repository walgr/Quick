package com.wpf.app.quick.demo

import android.widget.LinearLayout
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.ex.modelBinding
import com.wpf.app.quick.ability.ex.myLayout
import com.wpf.app.quick.ability.ex.with
import com.wpf.app.quick.annotations.getclass.GetClass
import com.wpf.app.quick.demo.databinding.ActivityRequestTestBinding
import com.wpf.app.quick.demo.viewmodel.RequestTestModel
import com.wpf.app.quickwork.ability.title

@GetClass
class RequestTestActivity : QuickActivity(
    contentView<LinearLayout> {
        title("接口测试页")
        myLayout(R.layout.activity_request_test)
    }.with(modelBinding<RequestTestModel, ActivityRequestTestBinding>())
)