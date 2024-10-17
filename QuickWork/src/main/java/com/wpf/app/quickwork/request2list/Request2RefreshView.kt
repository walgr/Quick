package com.wpf.app.quickwork.request2list

import com.wpf.app.quickbind.annotations.BindD2VHelper
import com.wpf.app.quickrecyclerview.data.QuickItemData

object Request2RefreshView :
    BindD2VHelper<QuickRefreshRecyclerView, Request2ListWithView<out RequestData, out QuickItemData, QuickRefreshRecyclerView>> {

    override fun initView(
        view: QuickRefreshRecyclerView,
        data: Request2ListWithView<out RequestData, out QuickItemData, QuickRefreshRecyclerView>
    ) {
        data.view = view
        view.setRequestManager(data)
    }
}