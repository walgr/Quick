package com.wpf.app.quickbind.plugins

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.wpf.app.quickbind.annotations.BindFragment2
import com.wpf.app.quickbind.viewpager2.adapter.Fragment2StateAdapter
import com.wpf.app.quickutil.bind.Bind
import java.lang.reflect.Field

class BindFragment2Plugin : BindBasePlugin {

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
            if (obj is Bind && obj !is AppCompatActivity) {
                obj.getView()?.let {
                    context = it.context
                }
            }
            if (context is FragmentActivity) {
                viewPager.adapter = Fragment2StateAdapter(context as FragmentActivity, viewModel, bindFragmentAnn)
            } else if (context is Fragment) {
                viewPager.adapter = Fragment2StateAdapter(context as Fragment, viewModel, bindFragmentAnn)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}