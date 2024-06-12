package com.wpf.app.quickwidget.selectview

import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickwidget.selectview.listeners.DataSelectOnAdapter
import com.wpf.app.quickwidget.selectview.listeners.OnSelectOnChange
import com.wpf.app.quickwidget.selectview.listeners.SetSelectChange

/**
 * Created by 王朋飞 on 2022/9/5.
 *
 */
open class QuickSelectAdapter : QuickAdapter(), DataSelectOnAdapter, SetSelectChange {
    var parentSelectAdapter: QuickSelectAdapter? = null
    var childSelectAdapter: QuickSelectAdapter? = null

    private var mOnSelectChange: OnSelectOnChange? = null

    override fun setOnSelectChangeListener(onSelectChange: OnSelectOnChange) {
        this.mOnSelectChange = onSelectChange
    }

    override fun getOnSelectChangeListener(): OnSelectOnChange? {
        return this.mOnSelectChange
    }
}