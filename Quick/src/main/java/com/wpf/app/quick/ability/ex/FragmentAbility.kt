package com.wpf.app.quick.ability.ex

import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.wpf.app.quick.R
import com.wpf.app.quick.ability.QuickActivityAbility
import com.wpf.app.quick.ability.setContentView
import com.wpf.app.quick.ability.with
import com.wpf.app.quickutil.bind.runOnContext
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.other.forceTo

inline fun <reified T : Fragment> fragment(
    fragment: T,
    noinline builder: (T.() -> Unit)? = null
): MutableList<QuickActivityAbility> {
    return setContentView(layoutViewInContext = runOnContext {
        val contentView = FrameLayout(it).apply {
            id = R.id.quickRoot
            layoutParams = matchLayoutParams
        }

        contentView
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