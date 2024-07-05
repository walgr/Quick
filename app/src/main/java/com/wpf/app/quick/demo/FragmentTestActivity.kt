package com.wpf.app.quick.demo

import com.wpf.app.quickutil.ability.ex.fragment
import com.wpf.app.quick.ability.QuickActivity

class FragmentTestActivity : QuickActivity(
    fragment(TestFragment())
)