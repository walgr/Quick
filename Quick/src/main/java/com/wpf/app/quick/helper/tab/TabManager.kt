package com.wpf.app.quick.helper.tab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.Tab
import com.wpf.app.quickutil.base.asTo
import com.wpf.app.quickutil.utils.onceClick

/**
 * 挪动位置需要同步修改TabInitProcessor里的包名
 */
open class TabManager : GroupManager() {

    private var curPos = 0
    override fun posChange(change: (curPos: Int) -> Unit): TabManager {
        super.posChange(change)
        return this
    }

    override fun posChange(curPos: Int) {
        super.posChange(curPos)
        init(layoutId, parent, size, curPos, repeatClick, false, init)
    }

    @LayoutRes
    private var layoutId: Int = 0
    private var parent: ViewGroup? = null
    private var size: Int = 0
    private var defaultPos: Int = 0
    private var repeatClick: Boolean = false
    private var init: ((curPos: Int, isSelect: Boolean, view: View) -> Unit)? = null
    private var isFirstInit = true
    fun init(
        @LayoutRes layoutId: Int = 0,
        parent: ViewGroup?,
        size: Int,
        defaultPos: Int = 0,
        repeatClick: Boolean = false,
        invokeChange: Boolean = true,
        init: ((curPos: Int, isSelect: Boolean, view: View) -> Unit)? = null
    ): GroupManager {
        this.layoutId = layoutId
        this.parent = parent
        this.size = size
        this.defaultPos = defaultPos
        this.repeatClick = repeatClick
        this.init = init
        if (parent == null) return this
        repeat(size) { pos ->
            val parentChild = getChildAt(parent, pos)
            val tabView = parentChild ?: LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            if (isFirstInit) {
                addChild(parent, tabView)
                (tabView.layoutParams as LinearLayout.LayoutParams).weight = 1f
                if (parent !is TabLayout) {
                    tabView.onceClick { tab ->
                        val viewPos = parent.indexOfChild(tab)
                        if (viewPos < 0 || viewPos >= size) return@onceClick
                        if (repeatClick || curPos != viewPos) {
                            repeat(size) { pos ->
                                init?.invoke(pos, pos == viewPos, parent.getChildAt(pos))
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
            parent.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: Tab?) {
                    repeat (parent.tabCount) {
                        if (tab != null) {
                            val view: Tab? = parent.getTabAt(it)
                            init?.invoke(it,  view == tab, tab.view)
                            if (view == tab) {
                                curPos = it
                                change?.invoke(curPos)
                            }
                        }
                    }
                }

                override fun onTabUnselected(tab: Tab?) {

                }

                override fun onTabReselected(tab: Tab?) {

                }

            })
        }
        if (invokeChange) {
            change?.invoke(defaultPos)
        }
        isFirstInit = false
        return this
    }

    private fun getChildAt(parent: ViewGroup, pos: Int): View? {
        return when(parent) {
            is TabLayout -> {
                parent.getTabAt(pos).asTo<Tab>()?.view
            }
            else -> {
                parent.getChildAt(pos)
            }
        }
    }
    private fun addChild(parent: ViewGroup, child: View) {
        when(parent) {
            is LinearLayout -> {
                parent.addView(child)
            }
            is TabLayout -> {
                parent.addTab(parent.newTab().setCustomView(child))
            }
        }
    }
}