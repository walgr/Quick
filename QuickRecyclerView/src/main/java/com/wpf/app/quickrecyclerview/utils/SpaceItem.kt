package com.wpf.app.quickrecyclerview.utils

import android.view.View
import com.wpf.app.quickbind.helper.binddatahelper.BindData2ViewHelper
import com.wpf.app.quickbind.helper.binddatahelper.Height2View
import com.wpf.app.quickbind.helper.binddatahelper.Width2View
import com.wpf.app.quickrecyclerview.R
import com.wpf.app.quickrecyclerview.data.QuickBindData

/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
open class SpaceItem(
    open val space: Int,
    open val isVertical: Boolean = true
): QuickBindData(R.layout.adapter_space) {

    override fun onCreateViewHolder(itemView: View) {
        super.onCreateViewHolder(itemView)
        BindData2ViewHelper.bind(itemView.findViewById(R.id.rootView),
        space, if (isVertical) Height2View else Width2View
        )
    }
}