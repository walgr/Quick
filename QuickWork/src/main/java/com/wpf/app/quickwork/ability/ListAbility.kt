package com.wpf.app.quickwork.ability

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.wpf.app.quickbind.helper.binddatahelper.BindData2ViewHelper
import com.wpf.app.quickrecyclerview.QuickRefreshRecyclerView
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData
import com.wpf.app.quickrecyclerview.helper.Request2RefreshView
import com.wpf.app.quickrecyclerview.listeners.RefreshView
import com.wpf.app.quickrecyclerview.listeners.Request2ListWithView
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.wrapContentHeightParams
import com.wpf.app.quickutil.other.forceTo

fun LinearLayout.smartRefreshLayout(
    header: RefreshHeader = ClassicsHeader(context),
    footer: RefreshFooter = ClassicsFooter(context),
    autoRefresh: Boolean = true,
    enableRefresh: Boolean = true,
    enableLoadMore: Boolean = true,
    layoutParams: ViewGroup.LayoutParams = wrapContentHeightParams,
    builder: SmartRefreshLayout.(list: QuickRefreshRecyclerView) -> Request2ListWithView<out RequestData, out QuickItemData, QuickRefreshRecyclerView>,
) {
    this.forceTo<ViewGroup>().smartRefreshLayout(
        header,
        footer,
        autoRefresh,
        enableRefresh,
        enableLoadMore,
        layoutParams,
        builder
    )
}

fun ViewGroup.smartRefreshLayout(
    header: RefreshHeader = ClassicsHeader(context),
    footer: RefreshFooter = ClassicsFooter(context),
    autoRefresh: Boolean = true,
    enableRefresh: Boolean = true,
    enableLoadMore: Boolean = true,
    layoutParams: ViewGroup.LayoutParams = matchLayoutParams,
    builder: SmartRefreshLayout.(list: QuickRefreshRecyclerView) -> Request2ListWithView<out RequestData, out QuickItemData, out RefreshView>,
) {
    val smartRefreshLayout = SmartRefreshLayout(context)
    val list = QuickRefreshRecyclerView(context)
    smartRefreshLayout.setRefreshHeader(header.forceTo())
    smartRefreshLayout.addView(list, matchLayoutParams)
    smartRefreshLayout.setRefreshFooter(footer.forceTo())
    smartRefreshLayout.setOnRefreshListener {
        list.onRefresh()
    }
    smartRefreshLayout.setOnLoadMoreListener {
        list.onLoadMore()
    }
    smartRefreshLayout.setEnableRefresh(enableRefresh)
    smartRefreshLayout.setEnableLoadMore(enableLoadMore)
    if (autoRefresh) {
        smartRefreshLayout.post {
            smartRefreshLayout.autoRefresh()
        }
    }
    addView(RelativeLayout(context).apply {
        addView(smartRefreshLayout, matchLayoutParams)
    }, layoutParams)
    val request2List = builder.invoke(smartRefreshLayout, list)
        .forceTo<Request2ListWithView<RequestData, QuickItemData, QuickRefreshRecyclerView>>()
    BindData2ViewHelper.bind(list, request2List, Request2RefreshView)
}