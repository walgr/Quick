package com.wpf.app.quick.ability.ex

import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
import com.google.android.material.appbar.AppBarLayout
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.parent
import com.wpf.app.quickutil.helper.wishLayoutParams

fun <F: View, T: View, B: View>ViewGroup.coordinator(
    layoutParams: ViewGroup.LayoutParams = smartLayoutParams(),
    followSlideLayout: (ViewGroup.() -> F)? = null,
    scrollFlags: Int? = null,
    topSuspendLayout: (ViewGroup.() -> T)? = null,
    bottomScrollLayout: ViewGroup.() -> B,
    behavior: Behavior<*> = AppBarLayout.ScrollingViewBehavior(),
    builder: (CoordinatorLayout.(followSlideLayout: F?, topSuspendLayout: T?, bottomScrollLayout: B) -> Unit)? = null
): CoordinatorLayout {
    val parentLayout = CoordinatorLayout(context)
    val appBarLayout = AppBarLayout(context)
    appBarLayout.elevation = 0f
    parentLayout.addView(appBarLayout, matchWrapLayoutParams)
    var followSlideLayoutView: F? = null
    var topSuspendLayoutView: T? = null
    followSlideLayout?.invoke(appBarLayout)?.let {
        it.wishLayoutParams<AppBarLayout.LayoutParams>().scrollFlags = scrollFlags!!
        followSlideLayoutView = it
        if (it.parent() != appBarLayout) {
            appBarLayout.addView(it, matchWrapLayoutParams)
        }
    }
    topSuspendLayout?.invoke(appBarLayout)?.let {
        topSuspendLayoutView = it
        if (it.parent() != appBarLayout) {
            appBarLayout.addView(it, matchWrapLayoutParams)
        }
    }
    var bottomScrollLayoutView: B
    bottomScrollLayout.invoke(parentLayout).let {
        it.wishLayoutParams<CoordinatorLayout.LayoutParams>().behavior = behavior
        bottomScrollLayoutView = it
        if (it.parent() != parentLayout) {
            parentLayout.addView(it, matchLayoutParams)
        }
    }
    builder?.invoke(parentLayout, followSlideLayoutView, topSuspendLayoutView, bottomScrollLayoutView)
    addView(parentLayout, layoutParams)
    return parentLayout
}