package com.wpf.app.quickbind.helper.binddatahelper

import android.view.View
import com.wpf.app.quickbind.annotations.BindD2VHHelper

/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
object BindData2ViewHelper {

    fun <Data: Any, V : View, Helper : BindD2VHHelper<V, Data>> bind(
        view: V,
        data: Data,
        helper: Helper
    ) {
        helper.initView(null, view, data)
    }
}