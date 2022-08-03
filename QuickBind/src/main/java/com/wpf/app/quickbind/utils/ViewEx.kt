package com.wpf.app.quickbind.utils

import android.view.View
import android.view.ViewParent
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager

/**
 * Created by 王朋飞 on 2022/7/27.
 * 请在onAttachedToWindow中执行
 * 返回view所在的Fragment或Activity
 */
fun View.getViewContext(): Any? {
    var viewParent: ViewParent? = parent ?: return null
    while (viewParent != null) {
        if (viewParent is ViewPager) {
            val fragments = (viewParent.context as AppCompatActivity).supportFragmentManager.fragments
            fragments.forEach {
                if (it.view?.findViewById<View>(id) != null) {
                    return it
                }
            }
        }
        if (viewParent == null) break
        viewParent = viewParent.parent
        if (viewParent == null) break
    }
    return null
}
