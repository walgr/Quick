package com.wpf.app.quick.ability.helper

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.wpf.app.quick.R
import com.wpf.app.quickrecyclerview.QuickRecyclerView
import com.wpf.app.quickrecyclerview.QuickRefreshRecyclerView
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickutil.widget.smartLayoutParams

fun ViewGroup.refreshList(
    layoutParams: ViewGroup.LayoutParams = smartLayoutParams(),
    layoutManager: LayoutManager = LinearLayoutManager(context),
    dataList: List<QuickItemData>? = null,
    builder: (QuickRecyclerView.() -> Unit)? = null
): QuickRefreshRecyclerView {
    return list(layoutParams, layoutManager, dataList, builder).forceTo()
}

fun ViewGroup.list(
    layoutParams: ViewGroup.LayoutParams = smartLayoutParams(),
    layoutManager: LayoutManager = LinearLayoutManager(context),
    dataList: List<QuickItemData>? = null,
    builder: (QuickRecyclerView.() -> Unit)? = null
): QuickRecyclerView {
    val list = QuickRefreshRecyclerView(context).apply {
        id = R.id.quickList
        this.layoutManager = layoutManager
        dataList?.let {
            setData(it.toMutableList())
        }
        builder?.invoke(this)
    }
    addView(list, layoutParams)
    return list
}