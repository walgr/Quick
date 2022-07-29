package com.wpf.app.quick.helper.binddatahelper

import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickbind.annotations.BindD2VHHelper

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class Select2CheckBox : BindD2VHHelper<CheckBox, Boolean> {

    override fun initView(viewHolder: RecyclerView.ViewHolder?, view: CheckBox, aBoolean: Boolean) {
        view.isChecked = aBoolean
    }
}