package com.wpf.app.quickrecyclerview.utils

import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import com.wpf.app.quickbind.helper.binddatahelper.BindData2ViewHelper
import com.wpf.app.quickbind.helper.binddatahelper.Color2View
import com.wpf.app.quickbind.helper.binddatahelper.Height2View
import com.wpf.app.quickrecyclerview.R

/**
 * Created by 王朋飞 on 2022/7/21.
 *
 */
open class LineItem(
    open val width: Int = 1,
    open val height: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    @ColorRes
    open val color: Int = 0,
    override val isVertical: Boolean = true
) : SpaceItem(space = width, isVertical) {

    override fun onCreateViewHolder(itemView: View) {
        super.onCreateViewHolder(itemView)
        BindData2ViewHelper.bind(itemView.findViewById(R.id.rootView), color, Color2View)
        BindData2ViewHelper.bind(itemView.findViewById(R.id.rootView), height, Height2View)
    }
}