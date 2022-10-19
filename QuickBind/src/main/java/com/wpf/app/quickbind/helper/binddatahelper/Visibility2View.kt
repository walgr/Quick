package com.wpf.app.quickbind.helper.binddatahelper

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickbind.annotations.BindD2VHHelper

/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
object Visibility2View :
    BindD2VHHelper<View, Int> {

    override fun initView(
        viewHolder: RecyclerView.ViewHolder?,
        view: View,
        data: Int
    ) {
        view.visibility = data
    }
}