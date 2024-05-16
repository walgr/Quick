package com.wpf.app.quickwork.ability.helper

import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.base.ability.scope.createViewGroupScope
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickutil.helper.matchWrapMarginLayoutParams

inline fun <reified T : ViewGroup> ViewGroupScope<out ViewGroup>.scroll(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
    noinline builder: (ViewGroupScope<T>.() -> Unit)? = null,
): ScrollView {
    val view = ScrollView(context)
    val childT = InitViewHelper.newInstance<T>(context)
    childT.layoutParams = matchMarginLayoutParams()
    view.addView(childT)
    builder?.invoke(createViewGroupScope(childT))
    addView(view, layoutParams)
    return view
}

inline fun <reified T : ViewGroup> ViewGroupScope<out ViewGroup>.nestedScroll(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
    noinline builder: (ViewGroupScope<T>.() -> Unit)? = null,
): NestedScrollView {
    val view = NestedScrollView(context)
    val childT = InitViewHelper.newInstance<T>(context)
    childT.layoutParams = matchMarginLayoutParams()
    view.addView(childT)
    builder?.invoke(createViewGroupScope(childT))
    addView(view, layoutParams)
    return view
}