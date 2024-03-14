package com.wpf.app.quick.demo

import com.wpf.app.quick.ability.QuickAbilityActivity
import com.wpf.app.quick.ability.ex.fragment
import com.wpf.app.quickwork.ability.title

class FragmentTestActivity : QuickAbilityActivity(
    fragment(TestFragment()) {
        title("加载Fragment")
    }
)