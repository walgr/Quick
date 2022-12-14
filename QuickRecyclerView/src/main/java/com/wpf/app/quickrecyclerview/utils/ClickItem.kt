package com.wpf.app.quickrecyclerview.utils

import com.wpf.app.quickbind.helper.binddatahelper.BindData2ViewHelper
import com.wpf.app.quickbind.helper.binddatahelper.ItemClick
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickbind.interfaces.RunItemClick
import com.wpf.app.quickbind.interfaces.RunItemClickWithSelf
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder

/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
open class ClickItem @JvmOverloads constructor(
    override val layoutId: Int,
    open val clickSelf: RunItemClickWithSelf<ClickItem>? = null,
    private val click: RunItemClick? = null,
) : QuickBindData(layoutId) {

    override fun onBindViewHolder(adapter: QuickAdapter, viewHolder: QuickViewHolder<QuickBindData>, position: Int) {
        super.onBindViewHolder(adapter, viewHolder, position)
        if (clickSelf != null || click != null) {
            BindData2ViewHelper.bind(viewHolder.itemView, clickSelf?.run(this) ?: click?.run()!!, ItemClick)
        }
    }
}