package com.wpf.app.quickbind.helper.binddatahelper

import android.view.View
import com.wpf.app.quickbind.annotations.BindD2VHelper

/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
object Visibility2View : BindD2VHelper<View, Int> {

    override fun initView(
        view: View,
        data: Int
    ) {
        view.visibility = data
    }
}