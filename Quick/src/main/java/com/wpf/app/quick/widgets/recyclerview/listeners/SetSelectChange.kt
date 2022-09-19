package com.wpf.app.quick.widgets.recyclerview.listeners

import com.wpf.app.quick.widgets.recyclerview.data.QuickChildSelectData

/**
 * Created by 王朋飞 on 2022/9/19.
 *
 */
interface SetSelectChange {
    fun setOnSelectChangeListener(onSelectChange: OnSelectOnChange)
    fun getOnSelectChangeListener(): OnSelectOnChange?
    fun getSelectList(): List<QuickChildSelectData>?
}