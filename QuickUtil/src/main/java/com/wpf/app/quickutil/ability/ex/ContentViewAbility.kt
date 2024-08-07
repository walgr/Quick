package com.wpf.app.quickutil.ability.ex

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import com.wpf.app.quickutil.Quick
import com.wpf.app.quickutil.ability.base.QuickAbility
import com.wpf.app.quickutil.ability.scope.ContextScope
import com.wpf.app.quickutil.ability.scope.QuickViewScope
import com.wpf.app.quickutil.ability.scope.ViewGroupScope
import com.wpf.app.quickutil.bind.QuickBindWrap
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.generic.asTo
import com.wpf.app.quickutil.helper.generic.forceTo
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickutil.helper.toView

interface ContentViewScope<Self : Quick, T : ViewGroup> : ViewGroupScope<T>,
    QuickViewScope<Self>

fun <Self : Quick, T : ViewGroup> createContentViewScope(context: Self, view: T) =
    object : ContentViewScope<Self, T> {
        override val view: T = view
        override val self: Self = context
    }

fun contentView(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewCreate: (ContextScope.() -> View)? = null,
    builder: (ContentViewScope<Quick, ViewGroup>.() -> Unit)? = null,
): MutableList<QuickAbility> {
    return generateContentViewCommon(layoutId, layoutView, layoutViewCreate) {
        builder?.invoke(createContentViewScope(this.self, it.forceTo()))
    }
}

inline fun <reified T : ViewGroup> contentView(
    layoutParams: LayoutParams = matchMarginLayoutParams(),
    noinline builder: (ContentViewScope<Quick, T>.() -> Unit)? = null,
): MutableList<QuickAbility> {
    return contentViewWithSelf<Quick, T>(layoutParams = layoutParams, builder = builder)
}

inline fun <reified Self : Quick, reified T : ViewGroup> contentViewWithSelf(
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
        QuickBindWrap.bind(this.self)
        builder?.invoke(createContentViewScope(this.self.forceTo(), childView))
        return@generateContentViewCommon childView
    })
}