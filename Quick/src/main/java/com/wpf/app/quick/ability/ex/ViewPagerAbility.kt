package com.wpf.app.quick.ability.ex

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.wpf.app.quick.R
import com.wpf.app.quick.activity.QuickView
import com.wpf.app.quick.helper.getFragmentManager
import com.wpf.app.quick.helper.toFragment
import com.wpf.app.quickbind.utils.getFragment
import com.wpf.app.quickbind.viewpager.QuickViewPager
import com.wpf.app.quickbind.viewpager.adapter.FragmentsAdapter
import com.wpf.app.quickbind.viewpager.adapter.FragmentsStateAdapter
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.other.forceTo
import kotlin.math.abs

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

inline fun <reified T : Fragment> ViewGroup.viewPager(
    layoutParams: ViewGroup.LayoutParams = smartLayoutParams(),
    quickView: QuickView,
    withState: Boolean = true,
    defaultSize: Int = 1,
    limit: Int = 0,
    isLoop: Boolean = false,
    noinline builder: (QuickViewPager.() -> Unit)? = null,
): ViewPager {
    val viewPager = QuickViewPager(context)
    viewPager.id = R.id.quickViewPager
    val defaultPos = if (isLoop) Int.MAX_VALUE / 2 else 0
    if (withState && !isLoop) {
        viewPager.adapter = FragmentsStateAdapter(quickView.getFragmentManager()) {
            getFragment(
                quickView, T::class.java.getDeclaredConstructor().newInstance().forceTo(), it
            ).forceTo()
        }.apply {
            setPageSize(defaultSize)
        }
    } else {
        viewPager.adapter = FragmentsAdapter(quickView.getFragmentManager()) {
            val realPos =
                if (isLoop) (abs(defaultSize + (it - defaultPos) % defaultSize) % defaultSize) else it
            getFragment(
                quickView, T::class.java.getDeclaredConstructor().newInstance().forceTo(), realPos
            ).forceTo()
        }.apply {
            setPageSize(if (isLoop) Int.MAX_VALUE else defaultSize)
            if (isLoop) {
                registerItemPositionChange {
                    return@registerItemPositionChange PagerAdapter.POSITION_NONE
                }
                registerItemIdChange {
                    System.currentTimeMillis() % Int.MAX_VALUE
                }
            }
        }
    }
    if (limit != 0) {
        viewPager.offscreenPageLimit = limit
    }
    addView(viewPager, layoutParams)
    builder?.invoke(viewPager)
    if (isLoop) {
        viewPager.setCurrentItem(defaultPos, false)
    }
    return viewPager
}

fun ViewGroup.viewPager(
    layoutParams: ViewGroup.LayoutParams = smartLayoutParams(),
    quickView: QuickView,
    fragments: List<Fragment>,
    withState: Boolean = true,
    limit: Int = 0,
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

fun ViewGroup.viewPagerWithView(
    layoutParams: ViewGroup.LayoutParams = smartLayoutParams(),
    quickView: QuickView,
    views: List<View>,
    withState: Boolean = true,
    limit: Int = 0,
    builder: (ViewPager.() -> Unit)? = null,
): ViewPager {
    val contentFragmentList = views.map {
        val contentView = FrameLayout(context)
        contentView.layoutParams = matchLayoutParams
        contentView.addView(it)
        contentView.toFragment()
    }
    return viewPager(layoutParams, quickView, contentFragmentList, withState, limit, builder)
}

fun ViewGroup.viewPager(
    layoutParams: ViewGroup.LayoutParams = smartLayoutParams(),
    quickView: QuickView,
    withState: Boolean = true,
    limit: Int = 0,
    viewConvert: ((view: View) -> Fragment)? = null,
    builder: (FragmentGroup.() -> Unit)? = null,
): ViewPager {
    val viewGroup = FragmentGroup(context, viewConvert)
    builder?.invoke(viewGroup)
    return viewPager(layoutParams, quickView, viewGroup.fragmentList, withState, limit)
}