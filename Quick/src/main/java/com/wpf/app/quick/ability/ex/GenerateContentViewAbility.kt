package com.wpf.app.quick.ability.ex

import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import com.wpf.app.base.QuickView
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.QuickInflateViewAbility
import com.wpf.app.quickutil.helper.match
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickutil.run.RunOnContext


@Deprecated("框架使用,建议使用contentView")
fun generateContentViewCommon(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    generateContentView: (QuickView.(view: View) -> View?)? = null,
    builder: (QuickView.(view: View) -> Unit)? = null,
): MutableList<QuickAbility> {
    return generateContentView(layoutId, layoutView, layoutViewInContext, generateContentView = {
        generateContentView?.invoke(this, it)
        null
    }, builder)
}

@Deprecated("框架使用,建议使用contentView")
fun generateContentView(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    generateContentView: (QuickView.(view: View) -> View?)? = null,
    builder: (QuickView.(view: View) -> Unit)? = null,
): MutableList<QuickAbility> {
    return mutableListOf(object : QuickInflateViewAbility {
        override fun layoutId(): Int {
            return layoutId
        }

        override fun layoutView(): View? {
            return layoutView
        }

        override fun layoutViewInContext(): RunOnContext<View>? {
            return layoutViewInContext
        }

        override fun generateContentView(owner: LifecycleOwner, view: View): View {
            if (view.layoutParams != null) {
                view.layoutParams?.width = match
                view.layoutParams?.height = match
            } else {
                view.layoutParams = matchLayoutParams()
            }
            return generateContentView?.invoke(owner.forceTo(), view) ?: view
        }

        override fun initView(owner: LifecycleOwner, view: View) {
            super.initView(owner, view)
            builder?.invoke(owner.forceTo(), view)
        }
    })
}