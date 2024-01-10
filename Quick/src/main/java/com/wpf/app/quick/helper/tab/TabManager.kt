package com.wpf.app.quick.helper.tab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.matchLayoutParams
import com.wpf.app.quickutil.other.onPageSelected
import com.wpf.app.quickutil.other.onTabSelected
import com.wpf.app.quickutil.other.onceClick
import com.wpf.app.quickutil.widgets.quickview.QuickViewGroup

/**
 * 挪动位置需要同步修改TabInitProcessor里的包名
 */
open class TabManager : GroupManager() {

    private var curPos = 0
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
        init(layoutId, parent, size, curPos, repeatClick, false, init)
    }

    fun bindViewPager(viewPager: ViewPager?, smoothScroll: Boolean = true): TabManager {
        if (viewPager == null) return this
        posChange { pos ->
            viewPager.setCurrentItem(pos, smoothScroll)
        }
        viewPager.onPageSelected {
            posChange(it)
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
    fun init(
        @LayoutRes layoutId: Int = 0,
        parent: ViewGroup?,
        size: Int,
        defaultPos: Int = 0,
        repeatClick: Boolean = false,
        invokeChange: Boolean = true,
        init: ((curPos: Int, isSelect: Boolean, view: View) -> Unit)? = null
    ): TabManager {
        this.layoutId = layoutId
        this.parent = parent
        this.size = size
        this.defaultPos = defaultPos
        this.repeatClick = repeatClick
        this.init = init
        if (parent == null) return this
        if (isFirstInit) {
            oldParentCount = getChildCount(parent)
        }
        init(parent, invokeChange)
        return this
    }

    private fun init(parent: ViewGroup, invokeChange: Boolean = true) {
        invokeChangeInInit = false
        repeat(size) { pos ->
            val realPos = pos + oldParentCount
            val tabView =
                getChildAt(parent, realPos)
                    ?: LayoutInflater.from(parent.context)
                        .inflate(layoutId, parent, false)
            if (isFirstInit) {
                addChild(parent, tabView)
                (tabView.layoutParams as? LinearLayout.LayoutParams)?.weight = 1f
                if (parent !is TabLayout) {
                    tabView.onceClick(250) { tab ->
                        val viewPos = indexOfChild(parent, tab) - oldParentCount
                        if (viewPos < 0 || viewPos >= size + oldParentCount) return@onceClick
                        if (repeatClick || curPos != viewPos) {
                            repeat(size) { pos ->
                                val realPos1 = pos + oldParentCount
                                init?.invoke(pos, pos == viewPos, getChildAt(parent, realPos1)!!)
                            }
                            curPos = viewPos
                            change?.invoke(viewPos)
                        }
                    }
                }
            }
            init?.invoke(pos, defaultPos == pos, tabView)
        }
        curPos = defaultPos
        if (parent is TabLayout && isFirstInit) {
            parent.onTabSelected { tab ->
                repeat(parent.tabCount) {
                    if (tab != null) {
                        val view: Tab? = parent.getTabAt(it)
                        init?.invoke(it, view == tab, tab.view)
                        if (view == tab) {
                            curPos = it
                            change?.invoke(curPos)
                        }
                    }
                }
            }
        }
        if (invokeChange && change != null) {
            change?.invoke(defaultPos)
            invokeChangeInInit = true
        }
        isFirstInit = false
    }

    private fun indexOfChild(parent: ViewGroup, child: View): Int {
        return when (parent) {
            is QuickViewGroup<*> -> {
                parent.indexOfChildInShadow(child)
            }

            else -> {
                parent.indexOfChild(child)
            }
        }
    }

    private fun getChildCount(parent: ViewGroup): Int {
        return when (parent) {
            is QuickViewGroup<*> -> {
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

            is QuickViewGroup<*> -> {
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

            is QuickViewGroup<*> -> {
                parent.addView(child)
            }

            else -> {
                parent.addView(child)
            }
        }
    }
}