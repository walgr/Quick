package com.wpf.app.quickutil.ability.helper

import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
import com.google.android.material.appbar.AppBarLayout
import com.wpf.app.quickutil.ability.scope.ContextScope
import com.wpf.app.quickutil.helper.layoutParams
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickutil.helper.matchWrapMarginLayoutParams
import com.wpf.app.quickutil.helper.parent
import com.wpf.app.quickutil.helper.removeParent
import com.wpf.app.quickutil.helper.wishLayoutParams

@Suppress("unused")
fun <Follow : View, Bottom : View> ContextScope.coordinator(
    layoutParams: ViewGroup.LayoutParams = matchMarginLayoutParams(),
    followSlideLayout: AppBarLayout.() -> Follow,
    scrollFlags: Int,
    bottomScrollLayout: CoordinatorLayout.() -> Bottom,
    behavior: Behavior<*> = AppBarLayout.ScrollingViewBehavior(),
    builder: (CoordinatorLayout.(followSlideLayout: Follow?, bottomScrollLayout: Bottom) -> Unit)? = null,
): CoordinatorLayout {
    return coordinator<Follow, View, Bottom>(
        layoutParams = layoutParams,
        followSlideLayout = followSlideLayout,
        scrollFlags = scrollFlags,
        bottomScrollLayout = bottomScrollLayout,
        behavior = behavior,
        builder = {followLayout, _, bottomLayout ->
            builder?.invoke(this, followLayout, bottomLayout)
        },
    )
}

fun <Follow : View, Top : View, Bottom : View> ContextScope.coordinator(
    layoutParams: ViewGroup.LayoutParams = matchMarginLayoutParams(),
    followSlideLayout: (AppBarLayout.() -> Follow)? = null,
    scrollFlags: Int? = null,
    topSuspendLayout: (AppBarLayout.() -> Top)? = null,
    bottomScrollLayout: CoordinatorLayout.() -> Bottom,
    behavior: Behavior<*> = AppBarLayout.ScrollingViewBehavior(),
    builder: (CoordinatorLayout.(followSlideLayout: Follow?, topSuspendLayout: Top?, bottomScrollLayout: Bottom) -> Unit)? = null,
): CoordinatorLayout {
    val parentLayout = CoordinatorLayout(context)
    val appBarLayout = AppBarLayout(context)
    parentLayout.addView(appBarLayout, matchWrapMarginLayoutParams())
    var followSlideLayoutView: Follow? = null

    followSlideLayout?.invoke(appBarLayout)?.let {
        followSlideLayoutView = it
        if (it.parent() != appBarLayout) {
            it.removeParent()
            appBarLayout.addView(
                it,
                it.layoutParams ?: layoutParams<AppBarLayout.LayoutParams>(
                    matchWrapMarginLayoutParams()
                )
            )
        }
        it.wishLayoutParams<AppBarLayout.LayoutParams>().scrollFlags = scrollFlags!!
    }
    var topSuspendLayoutView: Top? = null
    topSuspendLayout?.invoke(appBarLayout)?.let {
        topSuspendLayoutView = it
        if (it.parent() != appBarLayout) {
            it.removeParent()
            appBarLayout.addView(
                it,
                it.layoutParams ?: layoutParams<AppBarLayout.LayoutParams>(
                    matchWrapMarginLayoutParams()
                )
            )
        }
        it.wishLayoutParams<AppBarLayout.LayoutParams>().scrollFlags = 0
    }
    var bottomScrollLayoutView: Bottom
    bottomScrollLayout.invoke(parentLayout).let {
        bottomScrollLayoutView = it
        if (it.parent() != parentLayout) {
            it.removeParent()
            parentLayout.addView(
                it,
                it.layoutParams ?: layoutParams<CoordinatorLayout.LayoutParams>(
                    matchMarginLayoutParams()
                )
            )
        }
        it.wishLayoutParams<CoordinatorLayout.LayoutParams>().behavior = behavior
    }
    builder?.invoke(
        parentLayout,
        followSlideLayoutView,
        topSuspendLayoutView,
        bottomScrollLayoutView
    )
    addView(parentLayout, layoutParams)
    return parentLayout
}