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
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickutil.helper.toView
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo

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
    layoutParams: LayoutParams = matchMarginLayoutParams(),
    noinline builder: (ContentViewScope<QuickView, T>.() -> Unit)? = null,
): MutableList<QuickAbility> {
    return contentViewWithSelf<QuickView, T>(layoutParams = layoutParams, builder = builder)
}

inline fun <reified Self : QuickView> contentViewWithSelf(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    noinline layoutViewCreate: (ContextScope.() -> View)? = null,
    layoutParams: LayoutParams = matchMarginLayoutParams(),
    noinline builder: (ContentViewScope<Self, ViewGroup>.() -> Unit)? = null,
): MutableList<QuickAbility> {
    return contentViewWithSelf<Self, ViewGroup>(layoutId, layoutView, layoutViewCreate, layoutParams, builder)
}

inline fun <reified Self : QuickView, reified T : ViewGroup> contentViewWithSelf(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    noinline layoutViewCreate: (ContextScope.() -> View)? = null,
    layoutParams: LayoutParams = matchMarginLayoutParams(),
    noinline builder: (ContentViewScope<Self, T>.() -> Unit)? = null,
): MutableList<QuickAbility> {
    return generateContentViewCommon(layoutViewCreate = layoutViewCreate ?: {
        layoutView ?: (layoutId.takeIf { it != 0 })?.toView(context) ?: InitViewHelper.newInstance<T>(context)
    }, generateContentView = {
        val childView: T = it.forceTo()
        if (childView.layoutParams == null) {
            childView.layoutParams = layoutParams
        }
        childView.asTo<LinearLayout>()?.orientation = LinearLayout.VERTICAL
        QuickBindWrap.bind(this)
        builder?.invoke(createContentViewScope(this.self.forceTo(), childView))
        return@generateContentViewCommon childView
    })
}