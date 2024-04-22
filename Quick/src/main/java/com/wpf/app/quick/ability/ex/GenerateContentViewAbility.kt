package com.wpf.app.quick.ability.ex

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import com.wpf.app.base.QuickView
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.QuickInflateViewAbility
import com.wpf.app.base.ability.scope.ContextScope
import com.wpf.app.base.ability.scope.QuickViewScope
import com.wpf.app.base.ability.scope.createContextScope
import com.wpf.app.base.ability.scope.createQuickViewScope
import com.wpf.app.quickutil.other.forceTo

fun viewCreateConvert(layoutViewCreate: (ContextScope.() -> View)?) : (Context.() -> View)? {
    return layoutViewCreate?.let {
        object : (Context) -> View {
            override fun invoke(p1: Context): View {
                return it.invoke(createContextScope(p1))
            }
        }
    }
}

@Deprecated("框架使用,建议使用contentView")
fun generateContentViewCommon(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewCreate: (ContextScope.() -> View)? = null,
    generateContentView: (QuickViewScope<QuickView>.(view: View) -> View?)? = null,
    builder: (QuickViewScope<QuickView>.(view: View) -> Unit)? = null,
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
    generateContentView: (QuickViewScope<QuickView>.(view: View) -> View?)? = null,
    builder: (QuickViewScope<QuickView>.(view: View) -> Unit)? = null,
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

        override fun generateContentView(owner: LifecycleOwner, view: View): View {
            return generateContentView?.invoke(createQuickViewScope(owner.forceTo()), view) ?: view
        }

        override fun initView(owner: LifecycleOwner, view: View) {
            super.initView(owner, view)
            builder?.invoke(createQuickViewScope(owner.forceTo()), view)
        }
    })
}