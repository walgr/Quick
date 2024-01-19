package com.wpf.app.quick.demo.fragment

import com.wpf.app.quick.activity.QuickBindingFragment
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.databinding.FragmentMainTestBinding
import com.wpf.app.quickutil.view.dp2px
import com.wpf.app.quickutil.view.onProgressChanged

class MainTestFragment : QuickBindingFragment<FragmentMainTestBinding>(
    R.layout.fragment_main_test,
    titleName = "测试场"
) {
    override fun initView(view: FragmentMainTestBinding?) {
        super.initView(view)
        view?.llRoot?.postDelayed({
            view.llRoot.setNewItemSpaceWithAnim(20.dp2px(requireContext()))
        }, 2000)
        view?.seekbar?.onProgressChanged { _, progress, _ ->
            view.llRoot.setNewItemSpace(progress)
            view.ll1.setNewItemSpace(progress)
            view.ll2.setNewItemSpace(progress)
        }
    }
}