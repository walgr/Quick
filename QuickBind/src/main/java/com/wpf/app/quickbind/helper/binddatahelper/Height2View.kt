package com.wpf.app.quickbind.helper.binddatahelper

import android.view.View
import android.view.ViewGroup
import com.wpf.app.quickbind.annotations.BindD2VHelper

/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
object Height2View : BindD2VHelper<View, Int> {

    override fun initView(view: View, data: Int) {
        view.layoutParams?.let {
            it.height = data
            view.layoutParams = it
        }  ?: let {
            view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, data)
        }
    }

}