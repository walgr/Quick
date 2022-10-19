package com.wpf.app.quickrecyclerview.data

import android.view.View
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quickbind.helper.binddatahelper.ItemClick
import com.wpf.app.quickbind.interfaces.itemClick
import com.wpf.app.quickrecyclerview.data.QuickBindData

abstract class QuickClickData(
    override val layoutId: Int = 0,
    override val layoutView: View? = null,
    override val isSuspension: Boolean = false,
): QuickBindData() {

    @BindData2View(helper = ItemClick::class)
    private val itemClick = itemClick {
        onClick()
    }

    abstract fun onClick()
}