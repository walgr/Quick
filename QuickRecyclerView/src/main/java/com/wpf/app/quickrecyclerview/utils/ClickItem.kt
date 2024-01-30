package com.wpf.app.quickrecyclerview.utils

import com.wpf.app.quickbind.helper.binddatahelper.BindData2ViewHelper
import com.wpf.app.quickbind.helper.binddatahelper.ItemClick
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickutil.bind.RunItemClick
import com.wpf.app.quickutil.bind.RunItemClickWithSelf
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
open class ClickItem @JvmOverloads constructor(
    layoutId: Int,
    open val clickSelf: RunItemClickWithSelf<ClickItem>? = null,
    open val click: RunItemClick? = null,
) : QuickBindData(layoutId), Serializable {

    override fun onBindViewHolder(adapter: QuickAdapter, viewHolder: QuickViewHolder<QuickBindData>, position: Int) {
        super.onBindViewHolder(adapter, viewHolder, position)
        if (clickSelf != null || click != null) {
            BindData2ViewHelper.bind(viewHolder.itemView, clickSelf?.run(this) ?: click?.run()!!, ItemClick)
        }
    }
}