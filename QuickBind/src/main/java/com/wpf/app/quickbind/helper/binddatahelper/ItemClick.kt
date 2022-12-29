package com.wpf.app.quickbind.helper.binddatahelper

import android.view.View
import com.wpf.app.quickbind.annotations.BindD2VHelper

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
object ItemClick : BindD2VHelper<View, View.OnClickListener> {

    override fun initView(
        view: View,
        data: View.OnClickListener
    ) {
        view.setOnClickListener(data)
    }
}