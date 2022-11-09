package com.wpf.app.quickbind.plugins

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.wpf.app.quickbind.annotations.BindFragments2
import com.wpf.app.quickbind.interfaces.BindBaseFragment
import com.wpf.app.quickbind.viewpager2.adapter.FragmentsStateAdapter
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
                if (obj is FragmentActivity) {
                    viewPager.adapter = FragmentsStateAdapter(obj, getFragment(obj, bindFragmentsAnn.fragments))
                } else if (obj is Fragment) {
                    viewPager.adapter = FragmentsStateAdapter(obj, getFragment(obj, bindFragmentsAnn.fragments))
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
                val baseFragment: Fragment = fragmentCls.java.newInstance() as Fragment
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