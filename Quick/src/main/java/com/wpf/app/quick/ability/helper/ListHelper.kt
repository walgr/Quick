package com.wpf.app.quick.ability.helper

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.quick.R
import com.wpf.app.quickrecyclerview.QuickRecyclerView
import com.wpf.app.quickrecyclerview.QuickRefreshRecyclerView
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.utils.SpaceType

fun ViewGroupScope<out ViewGroup>.list(
    layoutParams: ViewGroup.LayoutParams = smartLayoutParams(),
    layoutManager: LayoutManager = LinearLayoutManager(context),
    space: Int? = null,
    spaceType: Int = SpaceType.Center.type,
    includeFirst: Boolean = false,
    includeLast: Boolean = false,
    dataList: List<QuickItemData>? = null,
    builder: (QuickRecyclerView.() -> Unit)? = null,
): QuickRecyclerView {
    val list = QuickRecyclerView(context).apply {
        id = R.id.quickList
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

fun ViewGroupScope<out ViewGroup>.refreshList(
    layoutParams: ViewGroup.LayoutParams = smartLayoutParams(),
    layoutManager: LayoutManager = LinearLayoutManager(context),
    space: Int? = null,
    spaceType: Int = SpaceType.Center.type,
    includeFirst: Boolean = false,
    includeLast: Boolean = false,
    dataList: List<QuickItemData>? = null,
    builder: (QuickRecyclerView.() -> Unit)? = null,
): QuickRefreshRecyclerView {
    val list = QuickRefreshRecyclerView(context).apply {
        id = R.id.quickList
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