package com.wpf.app.quick.helper.binddatahelper

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickbind.annotations.BindD2VHHelper

/**
 * Created by 王朋飞 on 2022/7/21.
 *
 */
class Color2TextView: BindD2VHHelper<TextView, Int> {

    override fun initView(viewHolder: RecyclerView.ViewHolder?, view: TextView, @ColorRes data: Int) {
        view.setTextColor(ContextCompat.getColor(view.context, data))
    }
}