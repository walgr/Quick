package com.wpf.app.quick.ability.ex

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import com.wpf.app.base.QuickView
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.scope.ContextScope
import com.wpf.app.base.ability.scope.QuickViewScope
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.base.bind.QuickBindWrap
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickutil.widget.QuickViewGroup

interface ContentViewScope<Self : QuickView, T : ViewGroup> : ViewGroupScope<T>,
    QuickViewScope<Self>

fun <Self : QuickView, T : ViewGroup> createContentViewScope(context: Self, view: T) =
    object : ContentViewScope<Self, T> {
        override val view: T = view
        override val self: Self = context
    }

fun contentView(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewCreate: (ContextScope.() -> View)? = null,
    builder: (ContentViewScope<QuickView, ViewGroup>.() -> Unit)? = null,
): MutableList<QuickAbility> {
    return generateContentViewCommon(layoutId, layoutView, layoutViewCreate) {
        builder?.invoke(createContentViewScope(this.self, it.forceTo()))
    }
}

inline fun <reified T : ViewGroup> contentView(
    layoutParams: LayoutParams = matchLayoutParams(),
    noinline builder: (ContentViewScope<QuickView, T>.() -> Unit)? = null,
): MutableList<QuickAbility> {
    return contentViewWithSelf<QuickView, T>(layoutParams = layoutParams, builder = builder)
}

inline fun <reified Self : QuickView, reified T : ViewGroup> contentViewWithSelf(
    layoutParams: LayoutParams = matchLayoutParams(),
    noinline builder: (ContentViewScope<Self, T>.() -> Unit)? = null,
): MutableList<QuickAbility> {
    return generateContentView(layoutViewCreate = {
        object : QuickViewGroup<T>(this.context, addToParent = true, forceGenerics = true) {}.shadowView!!
    }, generateContentView = {
        val childView: T = it.forceTo()
        childView.layoutParams = layoutParams
        childView.asTo<LinearLayout>()?.orientation = LinearLayout.VERTICAL
        QuickBindWrap.bind(this)
        builder?.invoke(createContentViewScope(this.self.forceTo(), childView))
        return@generateContentView childView
    })
}