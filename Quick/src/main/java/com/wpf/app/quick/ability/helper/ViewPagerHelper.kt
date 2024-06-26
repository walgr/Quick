package com.wpf.app.quick.ability.helper

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import com.wpf.app.base.Quick
import com.wpf.app.base.ability.helper.addView
import com.wpf.app.base.ability.scope.ContextScope
import com.wpf.app.quick.R
import com.wpf.app.quick.helper.getFragmentManager
import com.wpf.app.quick.helper.toFragment
import com.wpf.app.quickbind.utils.getFragment
import com.wpf.app.quickbind.viewpager.adapter.FragmentsAdapter
import com.wpf.app.quickbind.viewpager.adapter.FragmentsStateAdapter
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickwidget.viewpager.QuickViewPager
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

inline fun <reified F : Fragment> ContextScope.viewPager(
    layoutParams: ViewGroup.LayoutParams = matchMarginLayoutParams(),
    quick: Quick,
    withState: Boolean = true,
    defaultSize: Int = 1,
    limit: Int = 0,
    isLoop: Boolean = false,
    noinline fragmentDataInit: ((Int) -> Bundle)? = null,
    noinline builder: (QuickViewPager.() -> Unit)? = null,
): QuickViewPager {
    val viewPager = QuickViewPager(context)
    viewPager.id = R.id.quickViewPager
    val defaultPos = if (isLoop) Int.MAX_VALUE / 2 else 0
    if (withState && !isLoop) {
        viewPager.adapter =
            FragmentsStateAdapter(quick.getFragmentManager()) {
                val fragment = getFragment(
                    quick,
                    F::class.java.getDeclaredConstructor().newInstance().forceTo(),
                    it
                ).forceTo<Fragment>()
                fragmentDataInit?.apply {
                    fragment.arguments = fragmentDataInit.invoke(it)
                }
                fragment
            }.apply {
                setPageSize(defaultSize)
            }
    } else {
        viewPager.adapter = FragmentsAdapter(quick.getFragmentManager()) {
            val realPos =
                if (isLoop) (abs(defaultSize + (it - defaultPos) % defaultSize) % defaultSize) else it
            val fragment = getFragment(
                context,
                F::class.java.getDeclaredConstructor().newInstance().forceTo(),
                realPos
            ).forceTo<Fragment>()
            fragmentDataInit?.apply {
                fragment.arguments = fragmentDataInit.invoke(it)
            }
            fragment
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

fun ContextScope.viewPager(
    layoutParams: ViewGroup.LayoutParams = matchMarginLayoutParams(),
    quick: Quick,
    fragments: List<Fragment>,
    withState: Boolean = true,
    limit: Int = 0,
    builder: (QuickViewPager.() -> Unit)? = null,
): QuickViewPager {
    val viewPager = QuickViewPager(context)
    viewPager.id = R.id.quickViewPager
    if (withState) {
        viewPager.adapter = FragmentsStateAdapter(quick.getFragmentManager()) {
            fragments[it]
        }.apply {
            setPageSize(fragments.size)
        }
    } else {
        viewPager.adapter = FragmentsAdapter(quick.getFragmentManager()) {
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

@Suppress("unused")
fun ContextScope.viewPagerWithView(
    layoutParams: ViewGroup.LayoutParams = matchMarginLayoutParams(),
    quick: Quick,
    views: List<View>,
    withState: Boolean = true,
    limit: Int = 0,
    builder: (QuickViewPager.() -> Unit)? = null,
): QuickViewPager {
    val contentFragmentList = views.map {
        val contentView = FrameLayout(context)
        contentView.layoutParams = matchMarginLayoutParams()
        contentView.addView(it)
        contentView.toFragment()
    }
    return viewPager(layoutParams, quick, contentFragmentList, withState, limit, builder)
}

fun ContextScope.viewPagerBuilder(
    layoutParams: ViewGroup.LayoutParams = matchMarginLayoutParams(),
    quick: Quick,
    withState: Boolean = true,
    limit: Int = 0,
    viewConvert: ((view: View) -> Fragment)? = null,
    builder: (FragmentGroup.() -> Unit)? = null,
): QuickViewPager {
    val viewGroup = FragmentGroup(context, viewConvert)
    builder?.invoke(viewGroup)
    return viewPager(layoutParams, quick, viewGroup.fragmentList, withState, limit)
}