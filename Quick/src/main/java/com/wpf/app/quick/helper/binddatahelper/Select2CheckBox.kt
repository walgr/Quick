package com.wpf.app.quick.helper.binddatahelper

import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quick.annotations.BindD2VHelper
import com.wpf.app.quick.widgets.recyclerview.QuickBindData
import com.wpf.app.quick.widgets.recyclerview.QuickViewHolder

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class Select2CheckBox : BindD2VHelper<RecyclerView.ViewHolder, CheckBox, Boolean> {

    override fun initView(viewHolder: RecyclerView.ViewHolder?, view: CheckBox, aBoolean: Boolean) {
        view.isChecked = aBoolean
    }
}