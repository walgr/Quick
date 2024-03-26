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
) {
    this.forceTo<ViewGroup>().viewPager2(quickView, fragments, limit, layoutParams, builder)
}
fun ViewGroup.viewPager2(
    quickView: QuickView,
    fragments: List<Fragment>,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchLayoutParams,
    builder: (ViewPager2.() -> Unit)? = null,
) {
    val viewPager = ViewPager2(context)
    viewPager.id = R.id.quickViewPager
    viewPager.adapter =
        object : FragmentStateAdapter(quickView.getFragmentManager(), quickView.getLifecycle()),
            ViewPagerSize2 {
            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }

            override fun getItemCount(): Int {
                return if (getPageSize() != -1) {
                    getPageSize()
                } else {
                    fragments.size
                }
            }

            private var pageSizeI = -1
            override fun getPageSize(): Int {
                return pageSizeI
            }

            override fun setPageSize(size: Int) {
                pageSizeI = size
            }

            override fun getAdapter2(): FragmentStateAdapter {
                return this
            }
        }
    if (limit != 0) {
        viewPager.offscreenPageLimit = limit
    }
    addView(viewPager, layoutParams)
    builder?.invoke(viewPager)
}

inline fun <reified T : Fragment> LinearLayout.viewPager2(
    quickView: QuickView,
    defaultSize: Int = 1,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = wrapContentHeightParams,
    noinline builder: (ViewPager2.() -> Unit)? = null,
) {
    this.forceTo<ViewGroup>().viewPager2<T>(quickView, defaultSize, limit, layoutParams, builder)
}

inline fun <reified T : Fragment> ViewGroup.viewPager2(
    quickView: QuickView,
    defaultSize: Int = 1,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchLayoutParams,
    noinline builder: (ViewPager2.() -> Unit)? = null,
) {
    val viewPager = ViewPager2(context)
    viewPager.id = R.id.quickViewPager
    viewPager.adapter = object : FragmentStateAdapter(
        quickView.getFragmentManager(), quickView.getLifecycle()
    ), ViewPagerSize2 {
        override fun createFragment(i: Int): Fragment {
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

        override fun getItemCount(): Int {
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

        override fun getAdapter2(): FragmentStateAdapter {
            return this
        }
    }
    if (limit != 0) {
        viewPager.offscreenPageLimit = limit
    }
    addView(viewPager, layoutParams)
    builder?.invoke(viewPager)
}

fun ViewGroup.viewPager2(
    quickView: QuickView,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchLayoutParams,
    builder: ViewGroup.() -> Unit,
) {
    val viewGroup = FrameLayout(context)
    builder.invoke(viewGroup)
    val childViews = viewGroup.children.toList()
    childViews.forEach {
        it.removeParent()
    }
    viewPager2WithView(quickView, childViews, limit, layoutParams)
}

fun LinearLayout.viewPager2WithView(
    quickView: QuickView,
    views: List<View>,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = wrapContentHeightParams,
    builder: (ViewPager2.() -> Unit)? = null,
) {
    this.forceTo<ViewGroup>().viewPager2WithView(quickView, views, limit, layoutParams, builder)
}

fun ViewGroup.viewPager2WithView(
    quickView: QuickView,
    views: List<View>,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchLayoutParams,
    builder: (ViewPager2.() -> Unit)? = null,
) {
    val contentFragmentList = views.map {
        val contentView = FrameLayout(context)
        contentView.layoutParams = matchLayoutParams
        contentView.addView(it)
        contentView.toFragment()
    }
    viewPager2(quickView, contentFragmentList, limit, layoutParams, builder)
}