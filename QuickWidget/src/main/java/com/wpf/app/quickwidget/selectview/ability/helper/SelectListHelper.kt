package com.wpf.app.quickwidget.selectview.ability.helper

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.base.ability.helper.smartLayoutParams
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.utils.SpaceType
import com.wpf.app.quickwidget.selectview.QuickSelectRecyclerView


fun ViewGroupScope<out ViewGroup>.selectList(
    layoutParams: ViewGroup.LayoutParams = smartLayoutParams(),
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    space: Int? = null,
    spaceType: Int = SpaceType.Center.type,
    includeFirst: Boolean = false,
    includeLast: Boolean = false,
    dataList: List<QuickItemData>? = null,
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
    addView(list, layoutParams)
    return list
}