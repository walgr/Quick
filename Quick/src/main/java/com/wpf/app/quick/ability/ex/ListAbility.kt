package com.wpf.app.quick.ability.ex

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.wpf.app.quick.R
import com.wpf.app.quickrecyclerview.QuickRecyclerView
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.wrapContentHeightParams
import com.wpf.app.quickutil.other.forceTo

fun LinearLayout.list(
    layoutManager: LayoutManager = LinearLayoutManager(context),
    dataList: List<QuickItemData>? = null,
    layoutParams: LinearLayout.LayoutParams = wrapContentHeightParams,
    builder: (QuickRecyclerView.() -> Unit)? = null
) {
    this.forceTo<ViewGroup>().list(layoutManager, dataList, layoutParams, builder)
}

fun ViewGroup.list(
    layoutManager: LayoutManager = LinearLayoutManager(context),
    dataList: List<QuickItemData>? = null,
    layoutParams: ViewGroup.LayoutParams = matchWrapLayoutParams,
    builder: (QuickRecyclerView.() -> Unit)? = null
) {
    addView(QuickRecyclerView(context).apply {
        id = R.id.quickList
        this.layoutManager = layoutManager
        dataList?.let {
            setData(it.toMutableList())
        }
        builder?.invoke(this)
    }, layoutParams)
}