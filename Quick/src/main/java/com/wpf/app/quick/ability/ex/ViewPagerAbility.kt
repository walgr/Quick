package com.wpf.app.quick.ability.ex

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.wpf.app.quick.R
import com.wpf.app.quick.activity.QuickView
import com.wpf.app.quick.helper.getFragmentManager
import com.wpf.app.quick.helper.toFragment
import com.wpf.app.quickbind.interfaces.BindBaseFragment
import com.wpf.app.quickbind.utils.getFragment
import com.wpf.app.quickbind.viewpager.QuickViewPager
import com.wpf.app.quickbind.viewpager.adapter.FragmentsAdapter
import com.wpf.app.quickbind.viewpager.adapter.FragmentsStateAdapter
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.helper.wrapContentHeightParams
import com.wpf.app.quickutil.other.forceTo

inline fun <reified T : Fragment> LinearLayout.viewPager(
    quickView: QuickView,
    withState: Boolean = true,
    defaultSize: Int = -1,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = wrapContentHeightParams,
    noinline builder: (QuickViewPager.() -> Unit)? = null,
): ViewPager {
    return this.forceTo<ViewGroup>()
        .viewPager<T>(quickView, withState, defaultSize, limit, layoutParams, builder)
}

inline fun <reified T : Fragment> ViewGroup.viewPager(
    quickView: QuickView,
    withState: Boolean = true,
    defaultSize: Int = 1,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchLayoutParams,
    noinline builder: (QuickViewPager.() -> Unit)? = null,
): ViewPager {
    val viewPager = QuickViewPager(context)
    viewPager.id = R.id.quickViewPager
    val baseFragment =
        T::class.java.getDeclaredConstructor().newInstance().forceTo<BindBaseFragment>()
    if (withState) {
        viewPager.adapter = FragmentsStateAdapter(quickView.getFragmentManager()) {
            getFragment(quickView, baseFragment, it).forceTo()
        }.apply {
            setPageSize(defaultSize)
        }
    } else {
        viewPager.adapter = FragmentsAdapter(quickView.getFragmentManager()) {
            getFragment(quickView, baseFragment, it).forceTo()
        }.apply {
            setPageSize(defaultSize)
        }
    }
    if (limit != 0) {
        viewPager.offscreenPageLimit = limit
    }
    addView(viewPager, layoutParams)
    builder?.invoke(viewPager)
    return viewPager
}

fun LinearLayout.viewPager(
    quickView: QuickView,
    fragments: List<Fragment>,
    withState: Boolean = true,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = wrapContentHeightParams,
    builder: (QuickViewPager.() -> Unit)? = null,
): ViewPager {
    return this.forceTo<ViewGroup>()
        .viewPager(quickView, fragments, withState, limit, layoutParams, builder)
}

fun ViewGroup.viewPager(
    quickView: QuickView,
    fragments: List<Fragment>,
    withState: Boolean = true,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchLayoutParams,
    builder: (QuickViewPager.() -> Unit)? = null,
): ViewPager {
    val viewPager = QuickViewPager(context)
    viewPager.id = R.id.quickViewPager
    if (withState) {
        viewPager.adapter = FragmentsStateAdapter(quickView.getFragmentManager()) {
            fragments[it]
        }.apply {
            setPageSize(fragments.size)
        }
    } else {
        viewPager.adapter = FragmentsAdapter(quickView.getFragmentManager()) {
            fragments[it]
        }.apply {
            setPageSize(fragments.size)
        }
    }
    if (limit != 0) {
        viewPager.offscreenPageLimit = limit
    }
    addView(viewPager, layoutParams)
    builder?.invoke(viewPager)
    return viewPager
}

fun LinearLayout.viewPagerWithView(
    quickView: QuickView,
    views: List<View>,
    withState: Boolean = true,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = wrapContentHeightParams,
    builder: (ViewPager.() -> Unit)? = null,
): ViewPager {
    return this.forceTo<ViewGroup>()
        .viewPagerWithView(quickView, views, withState, limit, layoutParams, builder)
}

fun ViewGroup.viewPagerWithView(
    quickView: QuickView,
    views: List<View>,
    withState: Boolean = true,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchLayoutParams,
    builder: (ViewPager.() -> Unit)? = null,
): ViewPager {
    val contentFragmentList = views.map {
        val contentView = FrameLayout(context)
        contentView.layoutParams = matchLayoutParams
        contentView.addView(it)
        contentView.toFragment()
    }
    return viewPager(quickView, contentFragmentList, withState, limit, layoutParams, builder)
}

fun ViewGroup.viewPager(
    quickView: QuickView,
    withState: Boolean = true,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchLayoutParams,
    viewConvert: ((view: View) -> Fragment)? = null,
    builder: (FragmentGroup.() -> Unit)? = null,
): ViewPager {
    val viewGroup = FragmentGroup(context, viewConvert)
    builder?.invoke(viewGroup)
    return viewPager(quickView, viewGroup.fragmentList, withState, limit, layoutParams)
}


class FragmentGroup @JvmOverloads constructor(
    context: Context,
    private val viewConvert: ((view: View) -> Fragment)? = null,
) : ViewGroup(context) {
    internal val fragmentList = mutableListOf<Fragment>()
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }

    override fun addView(child: View?, index: Int, params: LayoutParams?) {
        child?.let {
            fragmentList.add(viewConvert?.invoke(it) ?: child.toFragment())
        }
    }

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }
}