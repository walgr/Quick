package com.wpf.app.quickwork.ability.helper

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
import com.wpf.app.base.ability.helper.addView
import com.wpf.app.base.ability.helper.viewCreateConvert
import com.wpf.app.base.ability.scope.ContextScope
import com.wpf.app.quickbind.helper.binddatahelper.BindData2ViewHelper
import com.wpf.app.quickrecyclerview.QuickRefreshRecyclerView
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData
import com.wpf.app.quickrecyclerview.helper.Request2RefreshView
import com.wpf.app.quickrecyclerview.listeners.Request2ListWithView
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickutil.helper.matchWrapMarginLayoutParams
import com.wpf.app.quickutil.helper.parent
import com.wpf.app.quickutil.helper.removeParent
import com.wpf.app.quickutil.other.forceTo

fun <V : View> ContextScope.smartRefreshLayout(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
    header: RefreshHeader = ClassicsHeader(context),
    footer: RefreshFooter = ClassicsFooter(context),
    autoRefresh: Boolean = true,
    enableRefresh: Boolean = true,
    enableLoadMore: Boolean = true,
    refreshListener: (RefreshLayout.(contentView: V) -> Unit)? = null,
    loadMoreListener: (RefreshLayout.(contentView: V) -> Unit)? = null,
    contentBuilder: SmartRefreshLayout.() -> V,
    builder: (SmartRefreshLayout.(contentView: V) -> Unit)? = null,
): SmartRefreshLayout {
    val smartRefreshLayout = SmartRefreshLayout(context)
    smartRefreshLayout.setRefreshHeader(header.forceTo())
    val contentView = contentBuilder.invoke(smartRefreshLayout)
    if (contentView.parent() != this) {
        contentView.removeParent()
        smartRefreshLayout.addView(contentView, matchMarginLayoutParams())
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

fun ContextScope.smartRefreshList(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    listBuilder: (QuickRefreshRecyclerView.() -> Unit)? = null,
    header: RefreshHeader = ClassicsHeader(context),
    footer: RefreshFooter = ClassicsFooter(context),
    autoRefresh: Boolean = true,
    enableRefresh: Boolean = true,
    enableLoadMore: Boolean = true,
    upperLayerLayoutId: Int = 0,
    upperLayerLayoutView: View? = null,
    upperLayerLayoutViewCreate: (ContextScope.() -> View)? = null,
    builder: SmartRefreshLayout.(list: QuickRefreshRecyclerView, upperLayout: View?) -> Request2ListWithView<out RequestData, out QuickItemData, QuickRefreshRecyclerView>,
): SmartRefreshLayout {
    val list = QuickRefreshRecyclerView(context)
    list.layoutManager = layoutManager
    listBuilder?.invoke(list)
    val upperLayout: View? =
        if (upperLayerLayoutId != 0 || upperLayerLayoutView != null || upperLayerLayoutViewCreate != null) {
            InitViewHelper.init(
                context,
                upperLayerLayoutId,
                upperLayerLayoutView,
                viewCreateConvert(upperLayerLayoutViewCreate)
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
                    addView(list, matchMarginLayoutParams())
                    addView(upperLayout, matchMarginLayoutParams())
                }
            } else {
                list
            }
        }) {
        BindData2ViewHelper.bind(list, builder.invoke(this, list, upperLayout), Request2RefreshView)
    }
}