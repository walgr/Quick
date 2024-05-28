package com.wpf.app.quickwidget.selectview.ability.helper

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.base.ability.helper.addView
import com.wpf.app.base.ability.scope.ContextScope
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.utils.SpaceType
import com.wpf.app.quickutil.helper.matchWrapMarginLayoutParams
import com.wpf.app.quickwidget.selectview.QuickSelectRecyclerView
import com.wpf.app.quickwidget.selectview.data.QuickSelectData
import com.wpf.app.quickwidget.selectview.listeners.OnSelectOnChange

fun ContextScope.selectList(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    space: Int? = null,
    spaceType: Int = SpaceType.Center.type,
    includeFirst: Boolean = false,
    includeLast: Boolean = false,
    dataList: List<QuickItemData>? = null,
    selectCallback: (List<QuickSelectData>?.() -> Unit)? = null,
    builder: (QuickSelectRecyclerView.() -> Unit)? = null,
): QuickSelectRecyclerView {
    val list = QuickSelectRecyclerView(context).apply {
        space?.let {
            setSpace(it, spaceType, includeFirst, includeLast)
        }
        this.layoutManager = layoutManager
        dataList?.let {
            setData(it.toMutableList())
        }
        builder?.invoke(this)
    }
    list.setOnSelectChangeListener(object : OnSelectOnChange {
        override fun onSelectChange() {
            selectCallback?.invoke(list.getSelectList())
        }
    })
    addView(list, layoutParams)
    return list
}