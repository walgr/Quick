package com.wpf.app.quickbind.plugins

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.wpf.app.quickutil.bind.Bind
import com.wpf.app.quickbind.annotations.BindFragment2
import com.wpf.app.quickbind.interfaces.BindBaseFragment
import com.wpf.app.quickbind.utils.getFragment
import com.wpf.app.quickbind.viewpager2.adapter.Fragments2StateAdapter
import com.wpf.app.quickutil.helper.generic.forceTo
import java.lang.reflect.Field

class BindFragment2Plugin : BindBasePlugin {

    override fun dealField(obj: Any, viewModel: ViewModel?, field: Field) {
        try {
            val bindFragmentAnn: BindFragment2 =
                field.getAnnotation(BindFragment2::class.java) ?: return
            field.isAccessible = true
            val viewPagerObj = field[getRealObj(obj, viewModel)]
            if (viewPagerObj !is ViewPager2) return
            val viewPager: ViewPager2 = viewPagerObj
            if (bindFragmentAnn.limit > 0) {
                viewPager.offscreenPageLimit = bindFragmentAnn.limit
            }
            var context = obj
            if (obj is Bind && obj !is AppCompatActivity) {
                obj.getView()?.let {
                    context = it.context
                }
            }
            val fragmentManager = if (context is FragmentActivity) {
                context.forceTo<FragmentActivity>().supportFragmentManager
            } else {
                context.forceTo<Fragment>().childFragmentManager
            }
            val lifecycleOwner = context.forceTo<LifecycleOwner>().lifecycle
            val bindBaseFragment =
                bindFragmentAnn.fragment.java.getDeclaredConstructor().newInstance()
                    .forceTo<BindBaseFragment>()
            viewPager.adapter = Fragments2StateAdapter(fragmentManager, lifecycleOwner) {
                getFragment(obj, bindBaseFragment, it).forceTo()
            }.apply {
                setPageSize(bindFragmentAnn.defaultSize)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}