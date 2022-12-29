package com.wpf.app.quickbind.helper.binddatahelper

import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.wpf.app.quickbind.annotations.BindD2VHelper


/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
object DrawableRes2View: BindD2VHelper<View, Int> {

    override fun initView(view: View, @DrawableRes data: Int) {
        view.background = ContextCompat.getDrawable(view.context, data)
    }
}