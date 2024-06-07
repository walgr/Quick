package com.wpf.app.quickrecyclerview.helper

import com.wpf.app.quickbind.annotations.BindD2VHelper
import com.wpf.app.quickrecyclerview.QuickRefreshRecyclerView
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData
import com.wpf.app.quickrecyclerview.listeners.Request2ListWithView

object Request2RefreshView :
    BindD2VHelper<QuickRefreshRecyclerView, Request2ListWithView<out RequestData, out QuickItemData, QuickRefreshRecyclerView>> {

    override fun initView(
        view: QuickRefreshRecyclerView,
        data: Request2ListWithView<out RequestData, out QuickItemData, QuickRefreshRecyclerView>
    ) {
        data.view = view
        data.requestData?.let {
            view.requestData = it
        }
        view.setDataChangeListener(data)
    }
}