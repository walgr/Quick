package com.wpf.app.quick.helper.binddatahelper

import android.widget.CheckBox
import com.wpf.app.quick.annotations.BindD2VHelper

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class Select2CheckBox : BindD2VHelper<CheckBox, Boolean> {

    override fun initView(view: CheckBox, aBoolean: Boolean) {
        view.isChecked = aBoolean
    }
}