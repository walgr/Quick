package com.wpf.app.quick.widgets.selectview.listeners

import com.wpf.app.quick.widgets.selectview.data.QuickChildSelectData

/**
 * Created by 王朋飞 on 2022/9/2.
 *
 */
interface OnSelectOnChange {
    fun onSelectChange()
}

interface OnSelectCallback {
    fun onSelectResult(selectResult: List<QuickChildSelectData>?)
}