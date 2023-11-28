package com.wpf.app.quickrecyclerview.helper

import com.wpf.app.quickbind.annotations.BindD2VHelper
import com.wpf.app.quicknetwork.utils.RequestCallback
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.QuickRefreshRecyclerView
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData
import com.wpf.app.quickrecyclerview.listeners.RefreshView
import com.wpf.app.quickrecyclerview.listeners.Request2ListWithView
import java.lang.reflect.ParameterizedType

object Request2RefreshView :
    BindD2VHelper<RefreshView, Request2ListWithView<out RequestData, out QuickItemData, out RefreshView>> {

    override fun initView(
        view: RefreshView,
        data: Request2ListWithView<out RequestData, out QuickItemData, out RefreshView>
    ) {
        if (view is QuickRefreshRecyclerView) {
            val dataAs = data as Request2ListWithView<RequestData, QuickItemData, QuickRefreshRecyclerView>
            dataAs.view = view
            dataAs.requestData?.let {
                view.requestData = it
            }
            view.setDataChangeListener(dataAs)
        } else if (view.getAdapter() is QuickAdapter) {
            val quickAdapter = view.getAdapter() as QuickAdapter
            val requestData: RequestData = ((data.javaClass.genericInterfaces[0] as ParameterizedType).actualTypeArguments[0] as Class<*>).getDeclaredConstructor().newInstance() as RequestData
            val realData = data as Request2ListWithView<RequestData, QuickItemData, RefreshView>
            realData.view = view
            realData.requestData = requestData
            realData.refreshCallback = object : RequestCallback<QuickItemData> {
                override fun backData(data: List<QuickItemData>?, hasMore: Boolean) {
                    view.refreshView?.onRefreshEnd(data)
                    quickAdapter.mDataList?.clear()
                    quickAdapter.appendList(data)
                    requestData.loadDataSize(data?.size ?: 0)
                    if (!realData.refreshFinish(hasMore)) {
                        quickAdapter.notifyDataSetChanged()
                    }
                }
            }
            realData.loadMoreCallback = object : RequestCallback<QuickItemData> {
                override fun backData(data: List<QuickItemData>?, hasMore: Boolean) {
                    view.refreshView?.onLoadMoreEnd(data)
                    quickAdapter.appendList(data)
                    requestData.loadDataSize(data?.size ?: 0)
                    if (!realData.loadMoreFinish(hasMore)) {
                        quickAdapter.notifyDataSetChanged()
                    }
                }
            }
            view.refreshView = object : RefreshView {
                override var refreshView: RefreshView? = this

                override fun onRefresh() {
                    super.onRefresh()
                    requestData.refresh()
                    realData.requestAndCallback(view, requestData, realData.refreshCallback!!)
                }

                override fun onLoadMore() {
                    super.onLoadMore()
                    requestData.loadMore()
                    realData.requestAndCallback(view, requestData, realData.loadMoreCallback!!)
                }
            }
        } else {
            //是普通RecyclerView
        }
    }
}