package com.wpf.app.quickrecyclerview.helper

import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickbind.annotations.BindD2VHHelper
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.data.QuickItemData


/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
object List2LinearLayout : BindD2VHHelper<LinearLayout, List<QuickItemData>> {

    override fun initView(
        viewHolder: RecyclerView.ViewHolder?,
        view: LinearLayout,
        data: List<QuickItemData>
    ) {
        val adapter = QuickAdapter()
        adapter.setNewData(data)
        adapter.mDataList?.forEachIndexed { index, quickItemData ->
            quickItemData.viewType = index
            val holder = adapter.onCreateViewHolder(view, index)
            adapter.onBindViewHolder(holder, index)
            view.addView(holder.itemView)
        }
    }
}