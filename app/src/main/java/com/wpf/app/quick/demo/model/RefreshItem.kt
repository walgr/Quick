package com.wpf.app.quick.demo.model

import android.annotation.SuppressLint
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quickbind.helper.binddatahelper.Text2TextView
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import kotlin.random.Random

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class RefreshItem : com.wpf.app.quickrecyclerview.data.QuickBindData(R.layout.holder_refresh_item) {

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.title, helper = Text2TextView::class)
    var title = "Title:${Random.nextInt(100)}"

//    @BindData2View(helper = ItemClick::class)
//    var itemClick = itemClick {
//        Toast.makeText(
//            it.context,
//            "点击" + getViewHolder()?.bindingAdapterPosition,
//            Toast.LENGTH_SHORT
//        ).show()
//    }

    override fun onBindViewHolder(
        adapter: com.wpf.app.quickrecyclerview.QuickAdapter,
        viewHolder: com.wpf.app.quickrecyclerview.holder.QuickViewHolder<com.wpf.app.quickrecyclerview.data.QuickBindData>,
        position: Int
    ) {
        super.onBindViewHolder(adapter, viewHolder, position)
//        LogUtil.e("onBindViewHolder-----" + this)
    }
}