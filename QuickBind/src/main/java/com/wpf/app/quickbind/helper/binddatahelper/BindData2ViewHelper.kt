package com.wpf.app.quickbind.helper.binddatahelper

import com.wpf.app.quickbind.annotations.BindD2VHelper

/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
object BindData2ViewHelper {

    fun <Data: Any, View, Helper : BindD2VHelper<View, Data>> bind(
        view: View,
        data: Data,
        helper: Helper
    ) {
        helper.initView(null, view, data)
    }
}