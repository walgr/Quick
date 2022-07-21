package com.wpf.app.quick.helper.binddatahelper

import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quick.widgets.recyclerview.QuickAdapter
import com.wpf.app.quick.widgets.recyclerview.QuickItemData
import com.wpf.app.quick.widgets.recyclerview.QuickRecyclerView
import com.wpf.app.quickbind.annotations.BindD2VHHelper


/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
class DrawableRes2View: BindD2VHHelper<View, Int> {

    override fun initView(viewHolder: RecyclerView.ViewHolder?, view: View, @DrawableRes data: Int) {
        view.background = ContextCompat.getDrawable(view.context, data)
    }
}