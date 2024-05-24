package com.wpf.app.quick.demo

import com.wpf.app.base.ability.ex.fragment
import com.wpf.app.quick.ability.QuickActivity

class FragmentTestActivity : QuickActivity(
    fragment(TestFragment())
)