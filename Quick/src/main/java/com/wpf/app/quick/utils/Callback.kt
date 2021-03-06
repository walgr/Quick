package com.wpf.app.quick.utils

import com.wpf.app.quick.widgets.recyclerview.QuickItemData

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
interface Callback<Data : QuickItemData> {

    fun callback(data: List<Data>?)
}