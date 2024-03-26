package com.wpf.app.quick.ability.ex

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.wpf.app.quick.R
import com.wpf.app.quickutil.bind.runOnContext
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.other.forceTo

fun ViewGroup.fragment(callback: ViewGroup.() -> Unit) {
    callback.invoke(this)
}

inline fun <reified T : Fragment> fragment(
    fragment: T,
    noinline builder: (T.() -> Unit)? = null
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