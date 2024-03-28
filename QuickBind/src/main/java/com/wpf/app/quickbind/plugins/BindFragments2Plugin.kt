package com.wpf.app.quickbind.plugins

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.wpf.app.quickbind.annotations.BindFragments2
import com.wpf.app.quickbind.interfaces.BindBaseFragment
import com.wpf.app.quickbind.viewpager.adapter.FragmentsStateAdapter
import com.wpf.app.quickbind.viewpager2.adapter.Fragments2StateAdapter
import com.wpf.app.quickutil.bind.Bind
import com.wpf.app.quickutil.other.forceTo
import java.lang.reflect.Field
import kotlin.reflect.KClass

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class BindFragments2Plugin : BindBasePlugin {

    override fun dealField(
        obj: Any,
        viewModel: ViewModel?,
        field: Field
    ) {
        try {
            val bindFragmentsAnn: BindFragments2 = field.getAnnotation(BindFragments2::class.java)
                ?: return
            field.isAccessible = true
            val viewPagerObj = field[getRealObj(obj, viewModel)]
            if (viewPagerObj is ViewPager2) {
                val viewPager: ViewPager2 = viewPagerObj
                if (bindFragmentsAnn.limit > 0) {
                    viewPager.offscreenPageLimit = bindFragmentsAnn.limit
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
                viewPager.adapter = Fragments2StateAdapter(fragmentManager, lifecycleOwner) {
                    bindFragmentsAnn.fragments[it].forceTo()
                }.apply {
                    setPageSize(bindFragmentsAnn.fragments.size)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getFragment(
        obj: Any,
        fragmentClsArray: Array<KClass<out BindBaseFragment>>
    ): List<BindBaseFragment> {
        val fragments: MutableList<BindBaseFragment> = ArrayList()
        for ((position, fragmentCls) in fragmentClsArray.withIndex()) {
            try {
                val baseFragment: Fragment = fragmentCls.java.getDeclaredConstructor().newInstance() as Fragment
                if (baseFragment is BindBaseFragment) {
                    if (obj is Activity) {
                        baseFragment.arguments = baseFragment.getInitBundle(obj, position)
                    } else if (obj is Fragment) {
                        baseFragment.arguments = baseFragment.getInitBundle(obj, position)
                    }
                    fragments.add(baseFragment)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return fragments
    }
}