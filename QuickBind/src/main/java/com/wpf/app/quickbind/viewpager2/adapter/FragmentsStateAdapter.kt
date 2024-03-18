package com.wpf.app.quickbind.viewpager2.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wpf.app.quickbind.interfaces.BindBaseFragment
import com.wpf.app.quickbind.viewpager2.ViewPagerSize2

class FragmentsStateAdapter : FragmentStateAdapter, ViewPagerSize2 {

    constructor(fragmentActivity: FragmentActivity, fragments: List<BindBaseFragment>) : super(fragmentActivity) {
        this.fragments = fragments
        this.context = fragmentActivity
    }

    constructor(fragment: Fragment, fragments: List<BindBaseFragment>) : super(fragment) {
        this.fragments = fragments
        this.context = fragment.requireContext()
    }

    private var context: Context?
    private var fragments: List<BindBaseFragment>

    override fun getItemCount(): Int {
        return if (pageSizeI == - 1) fragments.size else pageSizeI
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position] as Fragment
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