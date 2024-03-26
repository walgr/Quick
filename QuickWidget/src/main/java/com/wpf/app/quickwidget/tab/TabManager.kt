package com.wpf.app.quickwidget.tab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.widget.onPageSelected
import com.wpf.app.quickutil.widget.onTabSelected
import com.wpf.app.quickutil.helper.onceClick
import com.wpf.app.quickwidget.quickview.QuickViewGroupNoAttrs

/**
 * 挪动位置需要同步修改TabInitProcessor里的包名
 */


object TabManagerProvider {
    fun new() = TabManager()
}

open class TabManager : GroupManager() {

    private var curPos = 0
    private var invokeChange: Boolean = true
    private var invokeChangeInInit = false
    override fun posChange(change: (curPos: Int) -> Unit): TabManager {
        super.posChange(change)
        if (!isFirstInit && !invokeChangeInInit) {
            change.invoke(curPos)
        }
        return this
    }

    override fun posChange(curPos: Int) {
        super.posChange(curPos)
        invokeChange = false
        init(layoutId, parent, size, curPos, repeatClick, init)
    }

    private var viewPager: ViewPager? = null
    fun bindViewPager(viewPager: ViewPager?, smoothScroll: Boolean = true): TabManager {
        this.viewPager = viewPager
        if (viewPager == null) return this
        if (parent is BottomTabLayout) {
            parent?.asTo<BottomTabLayout>()?.bindViewPager(viewPager)
        }
        posChange { pos ->
            viewPager.setCurrentItem(pos, smoothScroll)
        }
        viewPager.onPageSelected {
            if (it != curPos) {
                posChange(it)
            }
        }
        return this
    }

    @LayoutRes
    private var layoutId: Int = 0
    private var parent: ViewGroup? = null
    private var size: Int = 0
    private var defaultPos: Int = 0
    private var repeatClick: Boolean = false
    private var init: ((curPos: Int, isSelect: Boolean, view: View) -> Unit)? = null

    private var isFirstInit = true
    private var oldParentCount = 0
    private var newInit: ((Int, Boolean, View) -> Unit)? = null
    fun init(
        @LayoutRes layoutId: Int = 0,
        parent: ViewGroup?,
        size: Int,
        defaultPos: Int = 0,
        repeatClick: Boolean = false,
        init: ((curPos: Int, isSelect: Boolean, view: View) -> Unit)? = null,
    ): TabManager {
        this.layoutId = layoutId
        this.parent = parent
        this.size = size
        this.defaultPos = defaultPos
        this.repeatClick = repeatClick
        this.init = init
        if (parent == null) return this
        var onGroupChangeListener: OnGroupChangeListener? = null
        if (isFirstInit) {
            oldParentCount = getChildCount(parent)
            if (parent is OnGroupChangeListener) {
                onGroupChangeListener = parent
            }
            if (parent is BottomTabLayout) {
                parent.asTo<BottomTabLayout>()?.bindViewPager(viewPager)
            }
            newInit = object : ((Int, Boolean, View) -> Unit) {
                override fun invoke(p1: Int, p2: Boolean, p3: View) {
                    init?.invoke(p1, p2, p3)
                    if (p2) {
                        onGroupChangeListener?.onChange(p3)
                    }
                }
            }
            invokeChangeInInit = false
        }
        val tabLayout = getTabLayout(parent)
        repeat(size) { pos ->
            val realPos = pos + oldParentCount
            val tabView =
                getChildAt(parent, realPos)
                    ?: LayoutInflater.from(parent.context)
                        .inflate(layoutId, parent, false)
            if (isFirstInit) {
                addChild(parent, tabView)
                (tabView.layoutParams as? LinearLayout.LayoutParams)?.weight = 1f
                if (tabLayout == null) {
                    tabView.onceClick(250) { tab ->
                        val viewPos = indexOfChild(parent, tab) - oldParentCount
                        if (viewPos < 0 || viewPos >= size + oldParentCount) return@onceClick
                        if (repeatClick || curPos != viewPos) {
                            repeat(size) { pos ->
                                val realPos1 = pos + oldParentCount
                                val childView = getChildAt(parent, realPos1)!!
                                newInit?.invoke(pos, pos == viewPos, childView)
                            }
                            curPos = viewPos
                            change?.invoke(curPos)
                        }
                    }
                }
            }
            newInit?.invoke(pos, defaultPos == pos, tabView)
        }
        curPos = defaultPos
        if (isFirstInit) {
            tabLayout?.apply {
                onTabSelected {
                    repeat(tabCount) {
                        val view: Tab? = getTabAt(it)
                        newInit?.invoke(it, view == this, this.view)
                        if (view == this) {
                            curPos = it
                            change?.invoke(curPos)
                        }
                    }
                }
            }
        }
        if (invokeChange) {
            change?.invoke(defaultPos)
            invokeChangeInInit = true
            invokeChange = false
        }
        isFirstInit = false
        return this
    }

    private fun getTabLayout(parent: ViewGroup): TabLayout? {
        if (parent is QuickViewGroupNoAttrs<*> && parent.shadowView is TabLayout) {
            return parent.shadowView?.asTo<TabLayout>()
        }
        return parent.asTo<TabLayout>()
    }

    private fun indexOfChild(parent: ViewGroup, child: View): Int {
        return when (parent) {
            is QuickViewGroupNoAttrs<*> -> {
                parent.indexOfChildInShadow(child)
            }

            else -> {
                parent.indexOfChild(child)
            }
        }
    }

    private fun getChildCount(parent: ViewGroup): Int {
        return when (parent) {
            is TabLayout -> {
                parent.tabCount
            }

            is QuickViewGroupNoAttrs<*> -> {
                parent.getRealChildCount()
            }

            else -> {
                parent.childCount
            }
        }
    }

    private fun getChildAt(parent: ViewGroup, pos: Int): View? {
        return when (parent) {
            is TabLayout -> {
                parent.getTabAt(pos).asTo<Tab>()?.view
            }

            is QuickViewGroupNoAttrs<*> -> {
                parent.getChildAtInShadow(pos)
            }

            else -> {
                parent.getChildAt(pos)
            }
        }
    }

    private fun addChild(parent: ViewGroup, child: View) {
        when (parent) {
            is TabLayout -> {
                parent.addTab(parent.newTab().setCustomView(child))
            }

            is QuickViewGroupNoAttrs<*> -> {
                parent.addView(child)
            }

            else -> {
                parent.addView(child)
            }
        }
    }
}