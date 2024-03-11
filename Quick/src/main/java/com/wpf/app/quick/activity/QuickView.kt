package com.wpf.app.quick.activity

import android.view.View

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
interface QuickView {
    fun getMyContentView(): View? = null

    fun setMyContentView(view: View) {

    }
}