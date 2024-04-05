package com.wpf.app.quickwork.ability

import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.wpf.app.quick.ability.ex.smartLayoutParams
import com.wpf.app.quickbind.helper.binddatahelper.BindData2ViewHelper
import com.wpf.app.quickrecyclerview.QuickRefreshRecyclerView
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData
import com.wpf.app.quickrecyclerview.helper.Request2RefreshView
import com.wpf.app.quickrecyclerview.listeners.RefreshView
import com.wpf.app.quickrecyclerview.listeners.Request2ListWithView
import com.wpf.app.quickutil.bind.RunOnContext
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.helper.parent
import com.wpf.app.quickutil.helper.removeParent
import com.wpf.app.quickutil.other.forceTo

fun <T : View> ViewGroup.smartRefreshLayout(
    layoutParams: ViewGroup.LayoutParams = smartLayoutParams(),
    header: RefreshHeader = ClassicsHeader(context),
    footer: RefreshFooter = ClassicsFooter(context),
    autoRefresh: Boolean = true,
    enableRefresh: Boolean = true,
    enableLoadMore: Boolean = true,
    refreshListener: (RefreshLayout.(contentView: T) -> Unit)? = null,
    loadMoreListener: (RefreshLayout.(contentView: T) -> Unit)? = null,
    contentBuilder: SmartRefreshLayout.() -> T,
    builder: (SmartRefreshLayout.(contentView: T) -> Unit)? = null,
): SmartRefreshLayout {
    val smartRefreshLayout = SmartRefreshLayout(context)
    smartRefreshLayout.setRefreshHeader(header.forceTo())
    val contentView = contentBuilder.invoke(smartRefreshLayout)
    if (contentView.parent() != this) {
        contentView.removeParent()
        smartRefreshLayout.addView(contentView, matchLayoutParams())
    }
    smartRefreshLayout.setRefreshFooter(footer.forceTo())
    smartRefreshLayout.setOnRefreshListener {
        refreshListener?.invoke(it, contentView)
    }
    smartRefreshLayout.setOnLoadMoreListener {
        loadMoreListener?.invoke(it, contentView)
    }
    smartRefreshLayout.setEnableRefresh(enableRefresh)
    smartRefreshLayout.setEnableLoadMore(enableLoadMore)
    if (autoRefresh) {
        smartRefreshLayout.post {
            smartRefreshLayout.autoRefresh()
        }
    }
    addView(smartRefreshLayout, layoutParams)
    builder?.invoke(smartRefreshLayout, contentView)
    return smartRefreshLayout
}

fun ViewGroup.smartRefreshList(
    layoutParams: ViewGroup.LayoutParams = smartLayoutParams(),
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    header: RefreshHeader = ClassicsHeader(context),
    footer: RefreshFooter = ClassicsFooter(context),
    autoRefresh: Boolean = true,
    enableRefresh: Boolean = true,
    enableLoadMore: Boolean = true,
    upperLayerLayoutId: Int = 0,
    upperLayerLayoutView: View? = null,
    upperLayerLayoutViewInContext: RunOnContext<View>? = null,
    builder: SmartRefreshLayout.(list: QuickRefreshRecyclerView, upperLayout: View?) -> Request2ListWithView<out RequestData, out QuickItemData, out RefreshView>,
): SmartRefreshLayout {
    val list = QuickRefreshRecyclerView(context)
    list.layoutManager = layoutManager
    val upperLayout: View? =
        if (upperLayerLayoutId != 0 || upperLayerLayoutView != null || upperLayerLayoutViewInContext != null) {
            InitViewHelper.init(
                context, upperLayerLayoutId, upperLayerLayoutView, upperLayerLayoutViewInContext
            )
        } else null
    return smartRefreshLayout(layoutParams,
        header,
        footer,
        autoRefresh,
        enableRefresh,
        enableLoadMore,
        refreshListener = { list.onRefresh() },
        loadMoreListener = { list.onLoadMore() },
        contentBuilder = {
            if (upperLayout != null) {
                RelativeLayout(context).apply {
                    addView(list, matchLayoutParams())
                    addView(upperLayout, matchLayoutParams())
                }
            } else {
                list
            }
        }) {
        val request2List = builder.invoke(this, list, upperLayout)
            .forceTo<Request2ListWithView<RequestData, QuickItemData, QuickRefreshRecyclerView>>()
        BindData2ViewHelper.bind(list, request2List, Request2RefreshView)
    }
}