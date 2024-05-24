package com.wpf.app.quickwidget.selectview.listeners

import com.wpf.app.quickwidget.selectview.data.QuickChildSelectData

/**
 * Created by 王朋飞 on 2022/9/19.
 *
 */
interface SetSelectChange {
    fun setOnSelectChangeListener(onSelectChange: OnSelectOnChange)
    fun getOnSelectChangeListener(): OnSelectOnChange?
    fun getSelectList(parentId: String? = null): List<QuickChildSelectData>?
}