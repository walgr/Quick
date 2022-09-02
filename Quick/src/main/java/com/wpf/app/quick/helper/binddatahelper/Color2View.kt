package com.wpf.app.quick.helper.binddatahelper

import android.view.View
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickbind.annotations.BindD2VHHelper


/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
object Color2View: BindD2VHHelper<View, Int> {

    override fun initView(viewHolder: RecyclerView.ViewHolder?, view: View, @DrawableRes data: Int) {
        view.setBackgroundColor(data)
    }
}