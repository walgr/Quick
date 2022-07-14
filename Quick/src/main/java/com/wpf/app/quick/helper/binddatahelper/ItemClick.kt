package com.wpf.app.quick.helper.binddatahelper

import android.view.View
import com.wpf.app.quick.annotations.BindD2VHelper

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class ItemClick : BindD2VHelper<View, View.OnClickListener> {

    override fun initView(view: View, onClickListener: View.OnClickListener) {
        view.setOnClickListener(onClickListener);
    }
}