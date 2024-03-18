package com.wpf.app.quickbind.viewpager2.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wpf.app.quickbind.annotations.BindFragment2
import com.wpf.app.quickbind.utils.getFragment
import com.wpf.app.quickbind.viewpager2.ViewPagerSize2
import com.wpf.app.quickutil.other.forceTo

class Fragment2StateAdapter : FragmentStateAdapter, ViewPagerSize2 {

    constructor(
        fragmentActivity: FragmentActivity,
        viewModel: ViewModel?,
        bindFragmentAnn: BindFragment2,
    ) : super(fragmentActivity) {
        this.context = fragmentActivity
        this.obj = fragmentActivity
        this.viewModel = viewModel
        this.bindFragmentAnn = bindFragmentAnn
    }

    constructor(fragment: Fragment, viewModel: ViewModel?, bindFragmentAnn: BindFragment2) : super(
        fragment
    ) {
        this.context = fragment.requireContext()
        this.obj = fragment
        this.viewModel = viewModel
        this.bindFragmentAnn = bindFragmentAnn
    }

    private var context: Context?
    private var obj: Any
    private var viewModel: ViewModel? = null
    private var bindFragmentAnn: BindFragment2

    override fun getItemCount(): Int {
        return if (getPageSize() == -1) bindFragmentAnn.defaultSize else getPageSize()
    }

    override fun createFragment(position: Int): Fragment {
        return getFragment(
            obj,
            bindFragmentAnn.fragment.java.getDeclaredConstructor()
                .newInstance().forceTo(),
            position
        ) as Fragment
    }

    private var pageSizeI = -1
    override fun getPageSize(): Int {
        return pageSizeI
    }

    override fun setPageSize(size: Int) {
        pageSizeI = size
    }

    override fun getAdapter2(): FragmentStateAdapter {
        return this
    }

    override fun currentContext(): Any? {
        return context
    }
}