package com.wpf.app.quick.ability.ex

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.wpf.app.quick.R
import com.wpf.app.quick.ability.QuickFragment
import com.wpf.app.quickutil.bind.runOnContext
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.other.forceTo

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

fun <T : Fragment> fragment(
    fragment: T,
    builder: (T.() -> Unit)? = null
): MutableList<QuickActivityAbility> {
    return setContentView(layoutViewInContext = runOnContext {
        FrameLayout(it).apply {
            id = R.id.quickRoot
            layoutParams = matchLayoutParams
        }
    }).with(object : QuickActivityAbility {
        override fun getPrimeKey() = "fragment"
        override fun initView(view: View) {
            super.initView(view)
            val activity: AppCompatActivity = view.context.forceTo()
            activity.supportFragmentManager.commit {
                add(R.id.quickRoot, fragment)
            }
            builder?.invoke(fragment)
        }
    })
}