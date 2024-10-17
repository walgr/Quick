package com.wpf.app.quickwork.ability.helper

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.wpf.app.quickutil.ability.helper.addView
import com.wpf.app.quickutil.ability.scope.ContextScope
import com.wpf.app.quickrecyclerview.QuickRecyclerView
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.utils.SpaceType
import com.wpf.app.quickutil.helper.matchWrapMarginLayoutParams
import com.wpf.app.quickwork.request2list.QuickRefreshRecyclerView

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