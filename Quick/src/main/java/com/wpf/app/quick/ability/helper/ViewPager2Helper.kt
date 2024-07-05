package com.wpf.app.quick.ability.helper

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.wpf.app.quickutil.Quick
import com.wpf.app.quickutil.ability.helper.addView
import com.wpf.app.quickutil.ability.scope.ContextScope
import com.wpf.app.quick.R
import com.wpf.app.quick.helper.getFragmentManager
import com.wpf.app.quick.helper.getLifecycle
import com.wpf.app.quick.helper.toFragment
import com.wpf.app.quickbind.utils.getFragment
import com.wpf.app.quickbind.viewpager2.adapter.Fragments2StateAdapter
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickutil.helper.removeParent
import com.wpf.app.quickutil.helper.generic.forceTo

fun ContextScope.viewPager2(
    layoutParams: ViewGroup.LayoutParams = matchMarginLayoutParams(),
    id: Int = R.id.quickViewPager,
    quick: Quick,
    fragments: List<Fragment>,
    limit: Int = 0,
    builder: (ViewPager2.() -> Unit)? = null,
): ViewPager2 {
    val viewPager2 = ViewPager2(context)
    viewPager2.id = id
    viewPager2.adapter =
        Fragments2StateAdapter(quick.getFragmentManager(), quick.getLifecycle()) {
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

@Suppress("unused")
inline fun <reified F : Fragment> ContextScope.viewPager2(
    layoutParams: ViewGroup.LayoutParams = matchMarginLayoutParams(),
    id: Int = R.id.quickViewPager,
    quick: Quick,
    defaultSize: Int = 1,
    limit: Int = 0,
    isLoop: Boolean = false,
    noinline fragmentDataInit: ((Int) -> Bundle)? = null,
    noinline builder: (ViewPager2.() -> Unit)? = null,
): ViewPager2 {
    val viewPager2 = ViewPager2(context)
    viewPager2.id = id
    val defaultPos = if (isLoop) Int.MAX_VALUE / 2 else 0
    var defaultSizeNew = defaultSize
    viewPager2.adapter =
        object : Fragments2StateAdapter(quick.getFragmentManager(), quick.getLifecycle(), {
            val realPos =
                if (isLoop) getRealPosInLoop(it, defaultSizeNew, defaultPos) else it
            val fragment = getFragment(
                quick,
                F::class.java.getDeclaredConstructor().newInstance().forceTo(),
                realPos
            ).forceTo<Fragment>()
            fragmentDataInit?.apply {
                fragment.arguments = fragmentDataInit.invoke(it)
            }
            fragment
        }) {
            override fun setPageSize(size: Int) {
                defaultSizeNew = size
                super.setPageSize(if (isLoop) Int.MAX_VALUE else size)
            }
        }.apply {
            setPageSize(defaultSizeNew)
            if (isLoop) {
                registerItemIdChange {
                    System.currentTimeMillis() % Int.MAX_VALUE
                }
            }
        }
    if (limit != 0) {
        viewPager2.offscreenPageLimit = limit
    }
    addView(viewPager2, layoutParams)
    builder?.invoke(viewPager2)
    if (isLoop) {
        viewPager2.setCurrentItem(defaultPos, false)
    }
    return viewPager2
}

@Suppress("unused")
inline fun <reified T : View> ContextScope.viewPager2WithView(
    layoutParams: ViewGroup.LayoutParams = matchMarginLayoutParams(),
    id: Int = R.id.quickViewPager,
    quick: Quick,
    defaultSize: Int = 1,
    limit: Int = 0,
    isLoop: Boolean = false,
    noinline viewInit: (T.(Int) -> Unit)? = null,
    noinline builder: (ViewPager2.() -> Unit)? = null,
): ViewPager2 {
    val viewPager2 = ViewPager2(context)
    viewPager2.id = id
    val defaultPos = if (isLoop) Int.MAX_VALUE / 2 else 0
    var defaultSizeNew = defaultSize
    viewPager2.adapter =
        object : Fragments2StateAdapter(quick.getFragmentManager(), quick.getLifecycle(), {
            val realPos =
                if (isLoop) getRealPosInLoop(it, defaultSizeNew, defaultPos) else it
            val view = InitViewHelper.newInstance<T>(context)
            viewInit?.invoke(view, realPos)
            val fragment = view.toFragment()
            fragment
        }) {
            override fun setPageSize(size: Int) {
                defaultSizeNew = size
                super.setPageSize(if (isLoop) Int.MAX_VALUE else size)
            }
        }.apply {
            setPageSize(defaultSizeNew)
            if (isLoop) {
                registerItemIdChange {
                    System.currentTimeMillis() % Int.MAX_VALUE
                }
            }
        }
    if (limit != 0) {
        viewPager2.offscreenPageLimit = limit
    }
    addView(viewPager2, layoutParams)
    builder?.invoke(viewPager2)
    if (isLoop) {
        viewPager2.setCurrentItem(defaultPos, false)
    }
    return viewPager2
}

fun ContextScope.viewPager2WithView(
    layoutParams: ViewGroup.LayoutParams = matchMarginLayoutParams(),
    id: Int = R.id.quickViewPager,
    quick: Quick,
    views: List<View>,
    limit: Int = 0,
    builder: (ViewPager2.() -> Unit)? = null,
): ViewPager2 {
    val contentFragmentList = views.map {
        val contentView = FrameLayout(context)
        contentView.layoutParams = matchMarginLayoutParams()
        contentView.addView(it)
        contentView.toFragment()
    }
    return viewPager2(layoutParams, id, quick, contentFragmentList, limit, builder)
}

@Suppress("unused")
fun ContextScope.viewPager2(
    layoutParams: ViewGroup.LayoutParams = matchMarginLayoutParams(),
    id: Int = R.id.quickViewPager,
    quick: Quick,
    limit: Int = 0,
    builder: ViewGroup.() -> Unit,
): ViewPager2 {
    val viewGroup = FrameLayout(context)
    builder.invoke(viewGroup)
    val childViews = viewGroup.children.toList()
    childViews.forEach {
        it.removeParent()
    }
    return viewPager2WithView(layoutParams, id, quick, childViews, limit)
}