package com.wpf.app.quickrecyclerview.helper

import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.QuickRecyclerView
import com.wpf.app.quickbind.annotations.BindD2VHelper


/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
object List2RecyclerView: BindD2VHelper<QuickRecyclerView, List<QuickItemData>> {

    override fun initView(
        view: QuickRecyclerView,
        data: List<QuickItemData>
    ) {
        view.setNewData(data)
    }
}