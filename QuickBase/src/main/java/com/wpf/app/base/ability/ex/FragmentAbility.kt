package com.wpf.app.base.ability.ex

import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.QuickInitViewAbility
import com.wpf.app.base.ability.base.with
import com.wpf.app.base.ability.helper.viewGroupCreate
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickutil.other.forceTo

@Suppress("DEPRECATION")
fun <T : Fragment> fragment(
    fragment: T,
    builder: (T.() -> Unit)? = null
): MutableList<QuickAbility> {
    return generateContentView(layoutViewCreate = {
        viewGroupCreate<FrameLayout>(layoutParams = matchMarginLayoutParams()) {
            view.id = androidx.fragment.R.id.fragment_container_view_tag
        }
    }).with(object : QuickInitViewAbility {
        override fun getPrimeKey() = "fragment"
        override fun initView(view: View) {
            super.initView(view)
            val activity: AppCompatActivity = view.context.forceTo()
            activity.supportFragmentManager.commit {
                add(androidx.fragment.R.id.fragment_container_view_tag, fragment)
            }
            builder?.invoke(fragment)
        }
    })
}