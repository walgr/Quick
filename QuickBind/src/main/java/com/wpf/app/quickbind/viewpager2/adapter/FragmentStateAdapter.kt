package com.wpf.app.quickbind.viewpager2.adapter

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wpf.app.quickbind.annotations.BindFragment2
import com.wpf.app.quickbind.interfaces.BindBaseFragment
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickbind.viewpager2.ViewPagerSize2
import com.wpf.app.quickutil.bind.Bind
import com.wpf.app.quickutil.helper.getViewContext

class FragmentStateAdapter : FragmentStateAdapter, ViewPagerSize2 {

    constructor(
        fragmentActivity: FragmentActivity,
        viewModel: ViewModel?,
        bindFragmentAnn: BindFragment2
    ) : super(fragmentActivity) {
        this.context = fragmentActivity
        this.obj = fragmentActivity
        this.viewModel = viewModel
        this.bindFragmentAnn = bindFragmentAnn
    }

    constructor(fragment: Fragment, viewModel: ViewModel?, bindFragmentAnn: BindFragment2) : super(fragment) {
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
        return getFragment(obj, viewModel, bindFragmentAnn, this, position) as Fragment
    }

    private var pageSizeI = -1
    override fun getPageSize(): Int {
        return pageSizeI
    }

    override fun setPageSize(size: Int) {
        pageSizeI = size
    }

    override fun getAdapter(): FragmentStateAdapter {
        return this
    }

    override fun currentContext(): Any? {
        return context
    }

    private fun getFragment(
        obj: Any,
        viewModel: ViewModel?,
        bindFragmentAnn: BindFragment2,
        fragmentAdapter: FragmentStateAdapter,
        position: Int
    ): BindBaseFragment {
        val baseFragment: BindBaseFragment =
            bindFragmentAnn.fragment.java.getDeclaredConstructor().newInstance() as BindBaseFragment
        if (obj is Bind) {
            val bundle = obj.bindData(position)
            if (bundle != null) {
                (baseFragment as Fragment).arguments = bundle
            } else {
                val viewContext =
                    (fragmentAdapter as? ViewPagerSize2)?.currentContext()
                        ?: obj.getView()?.getViewContext()
                if (viewContext is BindViewModel<*>) {
                    (baseFragment as Fragment).arguments =
                        baseFragment.getInitBundle(viewContext, position)
                } else {
                    if (viewContext is Activity) {
                        (baseFragment as Fragment).arguments =
                            baseFragment.getInitBundle(viewContext, position)
                    }
                    if (viewContext is Fragment) {
                        (baseFragment as Fragment).arguments =
                            baseFragment.getInitBundle(viewContext, position)
                    }
                }
            }
        } else {
            if (viewModel != null) {
                (baseFragment as Fragment).arguments =
                    baseFragment.getInitBundle(obj as BindViewModel<*>, position)
            } else {
                if (obj is Activity) {
                    (baseFragment as Fragment).arguments =
                        baseFragment.getInitBundle(obj, position)
                }
                if (obj is Fragment) {
                    (baseFragment as Fragment).arguments =
                        baseFragment.getInitBundle(obj, position)
                }
            }
        }
        return baseFragment
    }
}