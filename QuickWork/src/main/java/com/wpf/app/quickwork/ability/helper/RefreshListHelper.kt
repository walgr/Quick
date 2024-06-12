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
import com.wpf.app.base.ability.scope.ViewGroupScope
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
    smartRefreshLayout: SmartRefreshLayout? = null,
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
    val curSmartRefreshLayout = smartRefreshLayout ?: SmartRefreshLayout(context)
    curSmartRefreshLayout.setRefreshHeader(header.forceTo())
    val contentView = contentBuilder.invoke(curSmartRefreshLayout)
    if (contentView.parent() != this) {
        contentView.removeParent()
        curSmartRefreshLayout.addView(contentView, matchMarginLayoutParams())
    }
    curSmartRefreshLayout.setRefreshFooter(footer.forceTo())
    curSmartRefreshLayout.setOnRefreshListener {
        refreshListener?.invoke(it, contentView)
    }
    curSmartRefreshLayout.setOnLoadMoreListener {
        loadMoreListener?.invoke(it, contentView)
    }
    curSmartRefreshLayout.setEnableRefresh(enableRefresh)
    curSmartRefreshLayout.setEnableLoadMore(enableLoadMore)
    if (autoRefresh) {
        curSmartRefreshLayout.post {
            curSmartRefreshLayout.autoRefresh()
        }
    }
    addView(curSmartRefreshLayout, layoutParams)
    builder?.invoke(curSmartRefreshLayout, contentView)
    return curSmartRefreshLayout
}

@Suppress("UNCHECKED_CAST")
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
    upperLayerLayoutView: (SmartRefreshLayout.(QuickRefreshRecyclerView) -> View)? = null,
    upperLayerLayoutViewCreate: (ViewGroupScope<SmartRefreshLayout>.() -> View)? = null,
    builder: SmartRefreshLayout.(list: QuickRefreshRecyclerView, upperLayout: View?) -> Request2ListWithView<out RequestData, out QuickItemData, QuickRefreshRecyclerView>,
): SmartRefreshLayout {
    val smartRefreshLayout = SmartRefreshLayout(context)
    val list = QuickRefreshRecyclerView(context)
    list.layoutManager = layoutManager
    listBuilder?.invoke(list)
    val upperLayout: View? =
        if (upperLayerLayoutId != 0 || upperLayerLayoutView != null || upperLayerLayoutViewCreate != null) {
            InitViewHelper.init(
                context,
                upperLayerLayoutId,
                upperLayerLayoutView!!.invoke(smartRefreshLayout, list),
                viewCreateConvert(upperLayerLayoutViewCreate as? ContextScope.() -> View)
            )
        } else null
    return smartRefreshLayout(
        smartRefreshLayout,
        layoutParams,
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