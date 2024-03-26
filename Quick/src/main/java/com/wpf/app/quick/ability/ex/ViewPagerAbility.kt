package com.wpf.app.quick.ability.ex

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.wpf.app.quick.R
import com.wpf.app.quick.activity.QuickView
import com.wpf.app.quick.helper.getFragmentManager
import com.wpf.app.quick.helper.toFragment
import com.wpf.app.quickbind.utils.getFragment
import com.wpf.app.quickbind.viewpager.QuickViewPager
import com.wpf.app.quickbind.viewpager.ViewPagerSize
import com.wpf.app.quickbind.viewpager.adapter.FragmentsAdapter
import com.wpf.app.quickbind.viewpager.adapter.FragmentsStateAdapter
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.helper.removeParent
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
    if (withState) {
        viewPager.adapter = object : FragmentStatePagerAdapter(
            quickView.getFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ), ViewPagerSize {
            override fun getItem(i: Int): Fragment {
                try {
                    val fragment = getFragment(
                        quickView, T::class.java.getDeclaredConstructor().newInstance().forceTo(), i
                    )
                    return fragment as Fragment
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null!!
            }

            override fun getCount(): Int {
                return if (getPageSize() != -1) {
                    getPageSize()
                } else {
                    defaultSize
                }
            }

            private var pageSizeI = -1
            override fun getPageSize(): Int {
                return pageSizeI
            }

            override fun setPageSize(size: Int) {
                pageSizeI = size
            }

            override fun getAdapter() = this

            override fun getItemPosition(`object`: Any): Int {
                return (viewPager as? ViewPagerSize)?.getItemPosition(`object`) ?: POSITION_NONE
            }
        }
    } else {
        viewPager.adapter = object : FragmentPagerAdapter(
            quickView.getFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ), ViewPagerSize {
            override fun getItem(i: Int): Fragment {
                try {
                    val fragment = getFragment(
                        quickView, T::class.java.getDeclaredConstructor().newInstance().forceTo(), i
                    )
                    return fragment as Fragment
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null!!
            }

            override fun getCount(): Int {
                return if (getPageSize() != -1) {
                    getPageSize()
                } else {
                    defaultSize
                }
            }

            private var pageSizeI = -1
            override fun getPageSize(): Int {
                return pageSizeI
            }

            override fun setPageSize(size: Int) {
                pageSizeI = size
            }

            override fun getAdapter() = this

            override fun getItemPosition(`object`: Any): Int {
                return (viewPager as? ViewPagerSize)?.getItemPosition(`object`) ?: POSITION_NONE
            }
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
        viewPager.adapter = FragmentsStateAdapter(quickView.getFragmentManager(), fragments)
    } else {
        viewPager.adapter = FragmentsAdapter(quickView.getFragmentManager(), fragments)
    }
    if (limit != 0) {
        viewPager.offscreenPageLimit = limit
    }
    addView(viewPager, layoutParams)
    builder?.invoke(viewPager)
    return viewPager
}

fun ViewGroup.viewPager(
    quickView: QuickView,
    withState: Boolean = true,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchLayoutParams,
    builder: (ViewGroup.() -> Unit)? = null,
): ViewPager {
    val viewGroup = FrameLayout(context)
    builder?.invoke(viewGroup)
    val childViews = viewGroup.children.toList()
    childViews.forEach {
        it.removeParent()
    }
    return viewPagerWithView(quickView, childViews, withState, limit, layoutParams)
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