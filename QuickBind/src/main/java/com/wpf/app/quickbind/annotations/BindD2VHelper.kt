package com.wpf.app.quickbind.annotations

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quick.annotations.BindD2VHHelper

/**
 * Created by 王朋飞 on 2022/9/5.
 *
 */
interface BindD2VHelper<V : View, Data : Any> :
    BindD2VHHelper<RecyclerView.ViewHolder, V, Data> {

    override fun initView(viewHolder: RecyclerView.ViewHolder?, view: V, data: Data) {
        initView(view, data)
    }

    fun initView(view: V, data: Data)
}