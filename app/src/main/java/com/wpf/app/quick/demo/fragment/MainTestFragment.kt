package com.wpf.app.quick.demo.fragment

import com.wpf.app.quick.ability.QuickAbilityFragment
import com.wpf.app.quick.ability.ex.binding
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.with
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.databinding.FragmentMainTestBinding
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.postDelay
import com.wpf.app.quickutil.widget.onProgressChanged

class MainTestFragment : QuickAbilityFragment(
    contentView(R.layout.fragment_main_test)
        .with(binding<FragmentMainTestBinding> {
            llRoot.postDelay(2000) {
                llRoot.setNewItemSpaceWithAnim(20.dp())
                ll1.setNewItemSpaceWithAnim(20.dp())
                ll2.setNewItemSpaceWithAnim(20.dp())
            }
            seekbar.onProgressChanged { _, progress, _ ->
                llRoot.setNewItemSpace(progress)
                ll1.setNewItemSpace(progress)
                ll2.setNewItemSpace(progress)
            }
        })
)