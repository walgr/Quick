package com.wpf.app.quickrecyclerview.utils

import androidx.annotation.ColorInt
import com.wpf.app.quickbind.helper.binddatahelper.BindData2ViewHelper
import com.wpf.app.quickbind.helper.binddatahelper.Color2View
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/7/21.
 *
 */
open class LineItem(
    override val space: Int = 0,
    override val isVertical: Boolean = true,
    @ColorInt open val color: Int = 0
) : SpaceItem(space, isVertical), Serializable {

    override fun onBindViewHolder(adapter: QuickAdapter, viewHolder: QuickViewHolder<QuickBindData>, position: Int) {
        super.onBindViewHolder(adapter, viewHolder, position)
        BindData2ViewHelper.bind(viewHolder.itemView, color, Color2View)
    }
}