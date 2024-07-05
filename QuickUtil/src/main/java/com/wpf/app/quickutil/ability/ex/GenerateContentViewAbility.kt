package com.wpf.app.quickutil.ability.ex

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import com.wpf.app.quickutil.Quick
import com.wpf.app.quickutil.ability.base.QuickAbility
import com.wpf.app.quickutil.ability.base.QuickInflateViewAbility
import com.wpf.app.quickutil.ability.helper.viewCreateConvert
import com.wpf.app.quickutil.ability.scope.ContextScope
import com.wpf.app.quickutil.ability.scope.QuickViewScope
import com.wpf.app.quickutil.ability.scope.createQuickViewScope
import com.wpf.app.quickutil.helper.generic.forceTo

@Deprecated("框架使用,建议使用contentView")
fun generateContentViewCommon(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewCreate: (ContextScope.() -> View)? = null,
    generateContentView: (QuickViewScope<Quick>.(view: View) -> View?)? = null,
    builder: (QuickViewScope<Quick>.(view: View) -> Unit)? = null,
): MutableList<QuickAbility> {
    return generateContentView(layoutId, layoutView, layoutViewCreate, generateContentView = {
        generateContentView?.invoke(this, it)
        null
    }, builder)
}

@Deprecated("框架使用,建议使用contentView")
fun generateContentView(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewCreate: (ContextScope.() -> View)? = null,
    generateContentView: (QuickViewScope<Quick>.(view: View) -> View?)? = null,
    builder: (QuickViewScope<Quick>.(view: View) -> Unit)? = null,
): MutableList<QuickAbility> {
    return mutableListOf(object : QuickInflateViewAbility {
        override fun layoutId(): Int {
            return layoutId
        }

        override fun layoutView(): View? {
            return layoutView
        }

        override fun layoutViewCreate(): (Context.() -> View)? {
            return viewCreateConvert(layoutViewCreate)
        }

        override fun generateContentView(owner: Quick, view: View): View {
            return generateContentView?.invoke(createQuickViewScope(owner.forceTo()), view) ?: view
        }

        override fun initView(owner: Quick, view: View) {
            super.initView(owner, view)
            builder?.invoke(createQuickViewScope(owner.forceTo()), view)
        }
    })
}