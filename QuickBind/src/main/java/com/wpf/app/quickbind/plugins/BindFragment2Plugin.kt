package com.wpf.app.quickbind.plugins

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.wpf.app.quickbind.annotations.BindFragment2
import com.wpf.app.quickbind.interfaces.Bind
import com.wpf.app.quickbind.interfaces.BindBaseFragment
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickbind.utils.getViewContext
import com.wpf.app.quickbind.viewpager2.ViewPagerSize2
import com.wpf.app.quickbind.viewpager2.adapter.FragmentStateAdapter
import java.lang.reflect.Field

class BindFragment2Plugin : BasePlugin {

    override fun dealField(obj: Any, viewModel: ViewModel?, field: Field) {
        try {
            val bindFragmentAnn: BindFragment2 = field.getAnnotation(BindFragment2::class.java)
                ?: return
            field.isAccessible = true
            val viewPagerObj = field[getRealObj(obj, viewModel)]
            if (viewPagerObj !is ViewPager2) return
            val viewPager: ViewPager2 = viewPagerObj
            if (bindFragmentAnn.limit > 0) {
                viewPager.offscreenPageLimit = bindFragmentAnn.limit
            }
            var context = obj
            if (obj is Bind) {
                obj.getView()?.let {
                    context = it.context
                }
            }
            if (context is FragmentActivity) {
                viewPager.adapter = FragmentStateAdapter(context as FragmentActivity, viewModel, bindFragmentAnn)
            } else if (context is Fragment) {
                viewPager.adapter = FragmentStateAdapter(context as Fragment, viewModel, bindFragmentAnn)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getFragment(obj: Any, viewModel: ViewModel?, bindFragmentAnn: BindFragment2, viewPager: ViewPager2, position: Int): BindBaseFragment {
        val baseFragment: BindBaseFragment =
            bindFragmentAnn.fragment.java.newInstance() as BindBaseFragment
        if (obj is Bind) {
            val bundle = obj.bindData(position)
            if (bundle != null) {
                (baseFragment as Fragment).arguments = bundle
            } else {
                val viewContext =
                    (viewPager as? ViewPagerSize2)?.currentContext()
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