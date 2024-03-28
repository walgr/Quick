package com.wpf.app.quick.ability.ex

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.wpf.app.quick.R
import com.wpf.app.quick.activity.QuickView
import com.wpf.app.quick.helper.getFragmentManager
import com.wpf.app.quick.helper.getLifecycle
import com.wpf.app.quick.helper.toFragment
import com.wpf.app.quickbind.utils.getFragment
import com.wpf.app.quickbind.viewpager2.ViewPagerSize2
import com.wpf.app.quickbind.viewpager2.adapter.Fragments2StateAdapter
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.helper.removeParent
import com.wpf.app.quickutil.helper.wrapContentHeightParams
import com.wpf.app.quickutil.other.forceTo

fun LinearLayout.viewPager2(
    quickView: QuickView,
    fragments: List<Fragment>,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = wrapContentHeightParams,
    builder: (ViewPager2.() -> Unit)? = null,
): ViewPager2 {
    return this.forceTo<ViewGroup>().viewPager2(quickView, fragments, limit, layoutParams, builder)
}

fun ViewGroup.viewPager2(
    quickView: QuickView,
    fragments: List<Fragment>,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchLayoutParams,
    builder: (ViewPager2.() -> Unit)? = null,
): ViewPager2 {
    val viewPager2 = ViewPager2(context)
    viewPager2.id = R.id.quickViewPager
    viewPager2.adapter =
        Fragments2StateAdapter(quickView.getFragmentManager(), quickView.getLifecycle()) {
            fragments[it]
        }.apply {
            setPageSize(fragments.size)
        }
    if (limit != 0) {
        viewPager2.offscreenPageLimit = limit
    }
    addView(viewPager2, layoutParams)
    builder?.invoke(viewPager2)
    return viewPager2
}

inline fun <reified T : Fragment> LinearLayout.viewPager2(
    quickView: QuickView,
    defaultSize: Int = 1,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = wrapContentHeightParams,
    noinline builder: (ViewPager2.() -> Unit)? = null,
): ViewPager2 {
    return this.forceTo<ViewGroup>()
        .viewPager2<T>(quickView, defaultSize, limit, layoutParams, builder)
}

inline fun <reified T : Fragment> ViewGroup.viewPager2(
    quickView: QuickView,
    defaultSize: Int = 1,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchLayoutParams,
    noinline builder: (ViewPager2.() -> Unit)? = null,
): ViewPager2 {
    val viewPager2 = ViewPager2(context)
    viewPager2.id = R.id.quickViewPager
    viewPager2.adapter =
        Fragments2StateAdapter(quickView.getFragmentManager(), quickView.getLifecycle()) {
            getFragment(
                quickView, T::class.java.getDeclaredConstructor().newInstance().forceTo(), it
            ).forceTo()
        }.apply {
            setPageSize(defaultSize)
        }
    if (limit != 0) {
        viewPager2.offscreenPageLimit = limit
    }
    addView(viewPager2, layoutParams)
    builder?.invoke(viewPager2)
    return viewPager2
}

fun ViewGroup.viewPager2(
    quickView: QuickView,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchLayoutParams,
    builder: ViewGroup.() -> Unit,
): ViewPager2 {
    val viewGroup = FrameLayout(context)
    builder.invoke(viewGroup)
    val childViews = viewGroup.children.toList()
    childViews.forEach {
        it.removeParent()
    }
    return viewPager2WithView(quickView, childViews, limit, layoutParams)
}

fun LinearLayout.viewPager2WithView(
    quickView: QuickView,
    views: List<View>,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = wrapContentHeightParams,
    builder: (ViewPager2.() -> Unit)? = null,
): ViewPager2 {
    return this.forceTo<ViewGroup>()
        .viewPager2WithView(quickView, views, limit, layoutParams, builder)
}

fun ViewGroup.viewPager2WithView(
    quickView: QuickView,
    views: List<View>,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchLayoutParams,
    builder: (ViewPager2.() -> Unit)? = null,
): ViewPager2 {
    val contentFragmentList = views.map {
        val contentView = FrameLayout(context)
        contentView.layoutParams = matchLayoutParams
        contentView.addView(it)
        contentView.toFragment()
    }
    return viewPager2(quickView, contentFragmentList, limit, layoutParams, builder)
}