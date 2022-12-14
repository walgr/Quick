package com.wpf.app.quickbind.helper.binddatahelper

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickbind.annotations.BindD2VHelper

/**
 * Created by 王朋飞 on 2022/7/19.
 *
 */
object Drawable2ImageView : BindD2VHelper<ImageView, Int> {

    override fun initView(view: ImageView, data: Int) {
        if (data != 0) {
            view.setImageResource(data)
        }
    }

}