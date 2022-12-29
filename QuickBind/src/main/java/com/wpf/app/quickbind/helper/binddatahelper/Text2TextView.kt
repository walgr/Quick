package com.wpf.app.quickbind.helper.binddatahelper

import android.widget.TextView
import com.wpf.app.quickbind.annotations.BindD2VHelper

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
object Text2TextView : BindD2VHelper<TextView, CharSequence> {

    override fun initView(
        view: TextView,
        data: CharSequence
    ) {
        view.text = data
    }
}