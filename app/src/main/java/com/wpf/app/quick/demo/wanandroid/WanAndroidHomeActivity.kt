package com.wpf.app.quick.demo.wanandroid

import android.widget.LinearLayout
import com.wpf.app.quick.ability.QuickAbilityActivity
import com.wpf.app.quick.ability.ex.binding
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.ex.myLayout
import com.wpf.app.quick.ability.with
import com.wpf.app.quick.annotations.getclass.GetClass
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.databinding.ActivityWanandroidHomeBinding
import com.wpf.app.quickwork.ability.title

@GetClass
class WanAndroidHomeActivity : QuickAbilityActivity(
    contentView<LinearLayout> {
        title("WanAndroid")
        myLayout(R.layout.activity_wanandroid_home)
    }.with(binding<ActivityWanandroidHomeBinding>())
)