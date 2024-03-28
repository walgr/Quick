package com.wpf.app.quickbind.plugins

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import com.wpf.app.quickbind.annotations.BindFragment
import com.wpf.app.quickbind.interfaces.BindBaseFragment
import com.wpf.app.quickbind.utils.getFragment
import com.wpf.app.quickbind.viewpager.adapter.FragmentsAdapter
import com.wpf.app.quickbind.viewpager.adapter.FragmentsStateAdapter
import com.wpf.app.quickutil.bind.Bind
import com.wpf.app.quickutil.other.forceTo
import java.lang.reflect.Field

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class BindFragmentPlugin : BindBasePlugin {

    override fun dealField(
        obj: Any,
        viewModel: ViewModel?,
        field: Field,
    ) {
        try {
            val bindFragmentAnn: BindFragment =
                field.getAnnotation(BindFragment::class.java) ?: return
            field.isAccessible = true
            val viewPagerObj = field[getRealObj(obj, viewModel)]
            if (viewPagerObj is ViewPager) {
                val viewPager: ViewPager = viewPagerObj
                if (bindFragmentAnn.limit > 0) {
                    viewPager.offscreenPageLimit = bindFragmentAnn.limit
                }
                var fragmentManager: FragmentManager? = null
                var context = obj
                if (obj is Bind) {
                    obj.getView()?.let {
                        context = it.context
                    }
                }
                if (context is AppCompatActivity) {
                    fragmentManager = (context as AppCompatActivity).supportFragmentManager
                } else if (context is Fragment) {
                    fragmentManager = (context as Fragment).childFragmentManager
                }
                if (fragmentManager == null) return
                val bindBaseFragment = bindFragmentAnn.fragment.java.getDeclaredConstructor()
                    .newInstance().forceTo<BindBaseFragment>()
                if (bindFragmentAnn.withState) {
                    viewPager.adapter = FragmentsStateAdapter(fragmentManager) {
                        return@FragmentsStateAdapter getFragment(obj, bindBaseFragment, it) as Fragment
                    }.apply {
                        setPageSize(bindFragmentAnn.defaultSize)
                    }
                } else {
                    viewPager.adapter = FragmentsAdapter(fragmentManager) {
                        return@FragmentsAdapter getFragment(obj, bindBaseFragment, it) as Fragment
                    }.apply {
                        setPageSize(bindFragmentAnn.defaultSize)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}