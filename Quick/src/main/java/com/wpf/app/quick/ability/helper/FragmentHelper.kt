package com.wpf.app.quick.ability.helper

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.wpf.app.quick.ability.QuickFragment


fun FragmentGroup.viewFragment(callback: ViewGroup.() -> Unit) {
    val fragmentRootView = FrameLayout(context)
    callback.invoke(fragmentRootView)
    addView(fragmentRootView)
}

fun <T: QuickFragment> FragmentGroup.fragment(fragment: T, builder: (T.() -> Unit)? = null) {
    addFragment(fragment)
    builder?.invoke(fragment)
}

inline fun <reified T: QuickFragment> FragmentGroup.fragment(noinline builder: (T.() -> Unit)? = null) {
    val fragment = T::class.java.getDeclaredConstructor().newInstance()
    builder?.invoke(fragment)
    addFragment(fragment)
}

fun FragmentGroup.classFragment(fragmentClass: Class<*>, builder: (Fragment.() -> Unit)? = null) {
    val fragment = fragmentClass.getDeclaredConstructor().newInstance() as Fragment
    builder?.invoke(fragment)
    addFragment(fragment)
}