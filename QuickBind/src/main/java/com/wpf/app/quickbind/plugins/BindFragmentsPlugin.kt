package com.wpf.app.quickbind.plugins

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import com.wpf.app.quickbind.annotations.BindFragments
import com.wpf.app.quickbind.interfaces.BindBaseFragment
import com.wpf.app.quickbind.viewpager.adapter.FragmentsAdapter
import com.wpf.app.quickbind.viewpager.adapter.FragmentsStateAdapter
import java.lang.reflect.Field
import kotlin.reflect.KClass

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class BindFragmentsPlugin : BasePlugin {

    override fun dealField(
        obj: Any,
        viewModel: ViewModel?,
        field: Field
    ): Boolean {
        try {
            val bindFragmentsAnn: BindFragments = field.getAnnotation(BindFragments::class.java)
                ?: return false
            field.isAccessible = true
            val viewPagerObj = field[getRealObj(obj, viewModel)]
            if (viewPagerObj is ViewPager) {
                val viewPager: ViewPager = viewPagerObj
                if (bindFragmentsAnn.limit > 0) {
                    viewPager.offscreenPageLimit = bindFragmentsAnn.limit
                }
                var fragmentManager: FragmentManager? = null
                if (obj is AppCompatActivity) {
                    fragmentManager = obj.supportFragmentManager
                } else if (obj is Fragment) {
                    fragmentManager = obj.childFragmentManager
                }
                if (fragmentManager == null) return true
                if (bindFragmentsAnn.withState) {
                    viewPager.adapter = FragmentsStateAdapter(
                        fragmentManager,
                        getFragment(obj, bindFragmentsAnn.fragments)
                    )
                } else {
                    viewPager.adapter = FragmentsAdapter(
                        fragmentManager,
                        getFragment(obj, bindFragmentsAnn.fragments)
                    )
                }
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
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