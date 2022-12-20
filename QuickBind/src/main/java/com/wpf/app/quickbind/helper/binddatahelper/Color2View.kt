package com.wpf.app.quickbind.helper.binddatahelper

import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickbind.annotations.BindD2VHelper


/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
object Color2View: BindD2VHelper<View, Int> {

    override fun initView(view: View, @ColorInt data: Int) {
        view.setBackgroundColor(data)
    }
}