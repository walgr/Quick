package com.wpf.app.quick.demo

import android.widget.LinearLayout
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quickutil.ability.ex.contentView
import com.wpf.app.quick.ability.ex.modelBinding
import com.wpf.app.quickutil.ability.helper.viewGroupCreate
import com.wpf.app.quickutil.ability.base.with
import com.wpf.app.quick.annotations.getclass.GetClass
import com.wpf.app.quick.demo.databinding.ActivityRequestTestBinding
import com.wpf.app.quick.demo.viewmodel.RequestTestModel
import com.wpf.app.quickwork.ability.helper.title

@GetClass
class RequestTestActivity : QuickActivity(
    contentView<LinearLayout> {
        title("接口测试页")
        viewGroupCreate(R.layout.activity_request_test)
    }.with(modelBinding<RequestTestModel, ActivityRequestTestBinding>())
)