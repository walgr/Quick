package com.wpf.app.quick.ability.helper

import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
import com.google.android.material.appbar.AppBarLayout
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.base.ability.scope.createViewGroupScope
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.parent
import com.wpf.app.quickutil.helper.removeParent
import com.wpf.app.quickutil.widget.smartLayoutParams
import com.wpf.app.quickutil.widget.wishLayoutParams

fun <Follow : View, Top : View, Bottom : View> ViewGroupScope<out ViewGroup>.coordinator(
    layoutParams: ViewGroup.LayoutParams = this@coordinator.view.smartLayoutParams(),
    followSlideLayout: (ViewGroupScope<AppBarLayout>.() -> Follow)? = null,
    scrollFlags: Int? = null,
    topSuspendLayout: (ViewGroupScope<AppBarLayout>.() -> Top)? = null,
    bottomScrollLayout: ViewGroupScope<CoordinatorLayout>.() -> Bottom,
    behavior: Behavior<*> = AppBarLayout.ScrollingViewBehavior(),
    builder: (ViewGroupScope<CoordinatorLayout>.(followSlideLayout: Follow?, topSuspendLayout: Top?, bottomScrollLayout: Bottom) -> Unit)? = null,
): CoordinatorLayout {
    val parentLayout = CoordinatorLayout(context)
    val parentLayoutScope = createViewGroupScope(parentLayout)
    val appBarLayout = AppBarLayout(context)
    val appBarLayoutScope = createViewGroupScope(appBarLayout)
    parentLayout.addView(appBarLayout, matchWrapLayoutParams())
    var followSlideLayoutView: Follow? = null

    followSlideLayout?.invoke(appBarLayoutScope)?.let {
        followSlideLayoutView = it
        if (it.parent() != appBarLayout) {
            it.removeParent()
            appBarLayout.addView(it, it.layoutParams ?: matchWrapLayoutParams())
        }
        it.wishLayoutParams<AppBarLayout.LayoutParams>().scrollFlags = scrollFlags!!
    }
    var topSuspendLayoutView: Top? = null
    topSuspendLayout?.invoke(appBarLayoutScope)?.let {
        topSuspendLayoutView = it
        if (it.parent() != appBarLayout) {
            it.removeParent()
            appBarLayout.addView(it, it.layoutParams ?: matchWrapLayoutParams())
        }
        it.wishLayoutParams<AppBarLayout.LayoutParams>().scrollFlags = 0
    }
    var bottomScrollLayoutView: Bottom
    bottomScrollLayout.invoke(parentLayoutScope).let {
        bottomScrollLayoutView = it
        if (it.parent() != parentLayout) {
            it.removeParent()
            parentLayout.addView(it, it.layoutParams ?: matchLayoutParams())
        }
        it.wishLayoutParams<CoordinatorLayout.LayoutParams>().behavior = behavior
    }
    builder?.invoke(
        parentLayoutScope,
        followSlideLayoutView,
        topSuspendLayoutView,
        bottomScrollLayoutView
    )
    addView(parentLayout, layoutParams)
    return parentLayout
}