package com.wpf.app.quickbind.helper.binddatahelper

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickbind.annotations.BindD2VHelper

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
object Size2TextView : BindD2VHelper<TextView, Float> {

    override fun initView(
        view: TextView,
        data: Float
    ) {
        view.textSize = data
    }
}