package com.wpf.app.quick.demo

import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.ex.fragment

class FragmentTestActivity : QuickActivity(
    fragment(TestFragment())
)