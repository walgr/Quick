package com.wpf.app.quick.ability.ex

import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.QuickViewAbility
import com.wpf.app.base.ability.base.with
import com.wpf.app.quick.R
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.other.forceTo

fun <T : Fragment> fragment(
    fragment: T,
    builder: (T.() -> Unit)? = null
): MutableList<QuickAbility> {
    return generateContentView(layoutViewCreate = {
        FrameLayout(this).apply {
            id = R.id.quickRoot
            layoutParams = matchLayoutParams()
        }
    }).with(object : QuickViewAbility {
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