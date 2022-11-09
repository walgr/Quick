package com.wpf.app.quickrecyclerview.helper

import android.view.View
import com.wpf.app.quickbind.annotations.BindD2VHelper
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.QuickRefreshRecyclerView
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData
import com.wpf.app.quickrecyclerview.listeners.RefreshView
import com.wpf.app.quickrecyclerview.listeners.Request2ListWithView
import com.wpf.app.quickutil.CallbackList
import java.lang.reflect.ParameterizedType

object Request2RefreshView :
    BindD2VHelper<View, Request2ListWithView<out RequestData, out QuickItemData, out RefreshView>> {

    override fun initView(
        view: View,
        data: Request2ListWithView<out RequestData, out QuickItemData, out RefreshView>
    ) {
        if (view is QuickRefreshRecyclerView) {
            view.setDataChangeListener(data as Request2ListWithView<RequestData, QuickItemData, QuickRefreshRecyclerView>)
        } else if (view is RefreshView && view.getAdapter() is QuickAdapter) {
            val quickAdapter = view.getAdapter() as QuickAdapter
            val requestData: RequestData = try {
                ((data.javaClass.genericInterfaces[0] as ParameterizedType).actualTypeArguments[0] as Class<*>).newInstance() as RequestData
            } catch (ignore: Exception) {
                RequestData()
            }
            val realData = data as Request2ListWithView<RequestData, QuickItemData, RefreshView>
            view.refreshView = object : RefreshView {
                override var refreshView: RefreshView? = this

                override fun onRefresh() {
                    super.onRefresh()
                    requestData.refresh()
                    realData.requestAndCallback(view, requestData, object : CallbackList<QuickItemData> {
                        override fun callback(data: List<QuickItemData>?) {
                            quickAdapter.mDataList?.clear()
                            quickAdapter.appendList(data)
                            requestData.loadDataSize(data?.size ?: 0)
                            if (!realData.refreshFinish()) {
                                quickAdapter.notifyDataSetChanged()
                            }
                        }
                    })
                    realData.refreshFinish()
                }

                override fun onLoadMore() {
                    super.onLoadMore()
                    requestData.loadMore()
                    realData.requestAndCallback(view, requestData, object : CallbackList<QuickItemData> {
                        override fun callback(data: List<QuickItemData>?) {
                            quickAdapter.appendList(data)
                            requestData.loadDataSize(data?.size ?: 0)
                            if (!realData.loadMoreFinish()) {
                                quickAdapter.notifyDataSetChanged()
                            }
                        }
                    })
                    realData.loadMoreFinish()
                }
            }
        } else {
            //是普通RecyclerView
        }
    }
}