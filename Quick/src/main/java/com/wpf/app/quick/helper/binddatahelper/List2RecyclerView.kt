package com.wpf.app.quick.helper.binddatahelper

import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quick.widgets.recyclerview.data.QuickItemData
import com.wpf.app.quick.widgets.recyclerview.QuickRecyclerView
import com.wpf.app.quickbind.annotations.BindD2VHHelper


/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
object List2RecyclerView: BindD2VHHelper<QuickRecyclerView, List<QuickItemData>> {

    override fun initView(
        viewHolder: RecyclerView.ViewHolder?,
        view: QuickRecyclerView,
        data: List<QuickItemData>
    ) {
        view.setNewData(data)
    }
}