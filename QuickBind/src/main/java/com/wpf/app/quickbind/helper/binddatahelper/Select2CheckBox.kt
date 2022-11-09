package com.wpf.app.quickbind.helper.binddatahelper

import android.widget.CheckBox
import com.wpf.app.quickbind.annotations.BindD2VHelper

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
object Select2CheckBox : BindD2VHelper<CheckBox, Boolean> {

    override fun initView(view: CheckBox, data: Boolean) {
        view.isChecked = data
    }
}