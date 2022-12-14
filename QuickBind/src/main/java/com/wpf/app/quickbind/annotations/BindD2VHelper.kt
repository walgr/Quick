package com.wpf.app.quickbind.annotations

import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quick.annotations.BindD2VHHelper

/**
 * Created by 王朋飞 on 2022/9/5.
 *
 */
interface BindD2VHelper<View : Any, Data : Any> :
    BindD2VHHelper<RecyclerView.ViewHolder, View, Data> {

    override fun initView(viewHolder: RecyclerView.ViewHolder?, view: View, data: Data) {
        initView(view, data)
    }

    fun initView(view: View, data: Data)
}