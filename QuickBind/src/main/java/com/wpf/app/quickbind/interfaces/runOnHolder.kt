package com.wpf.app.quickbind.interfaces

import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface runOnHolder<V : View, Data> {

    fun run(view: V): Data
}