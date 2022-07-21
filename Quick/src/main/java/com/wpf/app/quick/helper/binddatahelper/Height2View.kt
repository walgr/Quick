package com.wpf.app.quick.helper.binddatahelper

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickbind.annotations.BindD2VHHelper

/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
class Height2View : BindD2VHHelper<View, Int> {

    override fun initView(viewHolder: RecyclerView.ViewHolder?, view: View, data: Int) {
        view.layoutParams?.let {
            it.height = data
            view.layoutParams = it
        }  ?: let {
            view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, data)
        }
    }

}