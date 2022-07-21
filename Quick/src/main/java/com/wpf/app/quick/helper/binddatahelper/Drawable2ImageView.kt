package com.wpf.app.quick.helper.binddatahelper

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickbind.annotations.BindD2VHHelper

/**
 * Created by 王朋飞 on 2022/7/19.
 *
 */
class Drawable2ImageView : BindD2VHHelper<ImageView, Int> {

    override fun initView(viewHolder: RecyclerView.ViewHolder?, view: ImageView, data: Int) {
        if (data != 0) {
            view.setImageResource(data)
        }
    }

}