package com.wpf.app.quick.widgets.selectview.listeners

import com.wpf.app.quick.widgets.selectview.data.QuickChildSelectData

/**
 * Created by 王朋飞 on 2022/9/19.
 *
 */
interface SetSelectChange {
    fun setOnSelectChangeListener(onSelectChange: OnSelectOnChange)
    fun getOnSelectChangeListener(): OnSelectOnChange?
    fun getSelectList(): List<QuickChildSelectData>?
}