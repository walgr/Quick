package com.wpf.app.quick.helper.binddatahelper

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickbind.annotations.BindD2VHHelper
import com.wpf.app.quickbind.interfaces.itemClickRun

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
object ItemClick : BindD2VHHelper<View, View.OnClickListener> {

    override fun initView(
        viewHolder: RecyclerView.ViewHolder?,
        view: View,
        data: View.OnClickListener
    ) {
        view.setOnClickListener(data)
    }
}