package com.wpf.app.base.ability.helper

import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import com.wpf.app.base.ability.scope.ContextScope
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.base.ability.scope.createViewGroupScope
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickutil.helper.matchWrapMarginLayoutParams

@Suppress("unused")
inline fun <reified T : ViewGroup> ContextScope.scroll(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
    noinline builder: (ViewGroupScope<T>.() -> Unit)? = null,
): ScrollView {
    val view = ScrollView(context)
    val childT = InitViewHelper.newInstance<T>(context)
    childT.layoutParams = matchMarginLayoutParams()
    view.addView(childT)
    builder?.invoke(childT.createViewGroupScope())
    addView(view, layoutParams)
    return view
}

@Suppress("unused")
inline fun <reified T : ViewGroup> ContextScope.nestedScroll(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
    noinline builder: (ViewGroupScope<T>.() -> Unit)? = null,
): NestedScrollView {
    val view = NestedScrollView(context)
    val childT = InitViewHelper.newInstance<T>(context)
    childT.layoutParams = matchMarginLayoutParams()
    view.addView(childT)
    builder?.invoke(childT.createViewGroupScope())
    addView(view, layoutParams)
    return view
}