package com.wpf.app.quick.widgets.recyclerview.listeners

import com.wpf.app.quick.widgets.recyclerview.data.QuickSelectData

/**
 * Created by 王朋飞 on 2022/9/2.
 *
 */
interface OnSelectOnChange {
    fun onSelect(selectList: List<QuickSelectData>?)
}