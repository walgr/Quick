package com.wpf.app.quickrecyclerview.utils

import android.view.View
import com.wpf.app.quickbind.helper.binddatahelper.BindData2ViewHelper
import com.wpf.app.quickbind.helper.binddatahelper.Height2View
import com.wpf.app.quickbind.helper.binddatahelper.Width2View
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickutil.run.runOnContextWithSelf
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
open class SpaceItem(
    open val space: Int,
    open val isVertical: Boolean = true
) : QuickBindData(layoutViewCreate = runOnContextWithSelf { context ->
    View(context)
}), Serializable {

    override fun onBindViewHolder(
        adapter: QuickAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int
    ) {
        super.onBindViewHolder(adapter, viewHolder, position)
        BindData2ViewHelper.bind(
            viewHolder.itemView,
            space,
            if (isVertical) Height2View else Width2View
        )
    }
}