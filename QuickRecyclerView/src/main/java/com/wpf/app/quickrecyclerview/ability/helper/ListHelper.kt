package com.wpf.app.quickrecyclerview.ability.helper

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.wpf.app.base.ability.helper.addView
import com.wpf.app.base.ability.scope.ContextScope
import com.wpf.app.quickrecyclerview.QuickRecyclerView
import com.wpf.app.quickrecyclerview.QuickRefreshRecyclerView
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.utils.SpaceType
import com.wpf.app.quickutil.helper.matchWrapMarginLayoutParams

fun ContextScope.list(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
    layoutManager: LayoutManager = LinearLayoutManager(context),
    space: Int? = null,
    spaceType: Int = SpaceType.Center.type,
    includeFirst: Boolean = false,
    includeLast: Boolean = false,
    dataList: List<QuickItemData>? = null,
    builder: (QuickRecyclerView.() -> Unit)? = null,
): QuickRecyclerView {
    val list = QuickRecyclerView(context).apply {
        space?.let {
            setSpace(it, spaceType, includeFirst, includeLast)
        }
        this.layoutManager = layoutManager
        dataList?.let {
            setData(it.toMutableList())
        }
        builder?.invoke(this)
    }
    addView(list, layoutParams)
    return list
}


fun ContextScope.refreshList(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
    layoutManager: LayoutManager = LinearLayoutManager(context),
    space: Int? = null,
    spaceType: Int = SpaceType.Center.type,
    includeFirst: Boolean = false,
    includeLast: Boolean = false,
    dataList: List<QuickItemData>? = null,
    builder: (QuickRecyclerView.() -> Unit)? = null,
): QuickRefreshRecyclerView {
    val list = QuickRefreshRecyclerView(context).apply {
        space?.let {
            setSpace(it, spaceType, includeFirst, includeLast)
        }
        this.layoutManager = layoutManager
        dataList?.let {
            setData(it.toMutableList())
        }
        builder?.invoke(this)
    }
    addView(list, layoutParams)
    return list
}