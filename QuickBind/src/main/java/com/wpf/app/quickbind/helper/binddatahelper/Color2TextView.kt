package com.wpf.app.quickbind.helper.binddatahelper

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickbind.annotations.BindD2VHelper

/**
 * Created by 王朋飞 on 2022/7/21.
 *
 */
object Color2TextView: BindD2VHelper<TextView, Int> {

    override fun initView(view: TextView, @ColorRes data: Int) {
        view.setTextColor(ContextCompat.getColor(view.context, data))
    }
}