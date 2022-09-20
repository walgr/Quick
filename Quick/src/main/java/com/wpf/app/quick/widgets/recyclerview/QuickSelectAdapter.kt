package com.wpf.app.quick.widgets.recyclerview

import com.wpf.app.quick.widgets.selectview.data.QuickSelectData
import com.wpf.app.quick.widgets.recyclerview.listeners.DataSelectOnAdapter
import com.wpf.app.quick.widgets.recyclerview.listeners.OnSelectOnChange
import com.wpf.app.quick.widgets.recyclerview.listeners.SetSelectChange

/**
 * Created by 王朋飞 on 2022/9/5.
 *
 */
open class QuickSelectAdapter : QuickAdapter(), DataSelectOnAdapter, SetSelectChange {
    var parentSelectAdapter: QuickSelectAdapter? = null
    var childSelectAdapter: QuickSelectAdapter? = null

    //在父类表示当前切换的项
    var curClickData: QuickSelectData? = null
        set(value) {
            field = value
            value?.onClickChange(true)
        }

    private var mOnSelectChange: OnSelectOnChange? = null

    override fun setOnSelectChangeListener(onSelectChange: OnSelectOnChange) {
        this.mOnSelectChange = onSelectChange
    }

    override fun getOnSelectChangeListener(): OnSelectOnChange? {
        return this.mOnSelectChange
    }
}