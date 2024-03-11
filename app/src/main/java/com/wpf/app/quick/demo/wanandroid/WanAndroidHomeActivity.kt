package com.wpf.app.quick.demo.wanandroid

import com.wpf.app.quick.ability.QuickAbilityActivity
import com.wpf.app.quick.ability.inLinearLayout
import com.wpf.app.quick.ability.binding
import com.wpf.app.quick.ability.with
import com.wpf.app.quick.annotations.getclass.GetClass
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.databinding.ActivityWanandroidHomeBinding
import com.wpf.app.quickwork.activity.contentWithTitle

@GetClass
class WanAndroidHomeActivity : QuickAbilityActivity(
    contentWithTitle(R.layout.activity_wanandroid_home, titleName = "WanAndroid")
        .with(binding<ActivityWanandroidHomeBinding> {})
)