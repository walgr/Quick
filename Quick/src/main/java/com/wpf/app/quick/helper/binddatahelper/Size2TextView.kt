package com.wpf.app.quick.helper.binddatahelper

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickbind.annotations.BindD2VHHelper

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
object Size2TextView :
    BindD2VHHelper<TextView, Float> {

    override fun initView(
        viewHolder: RecyclerView.ViewHolder?,
        view: TextView,
        data: Float
    ) {
        view.textSize = data
    }
}