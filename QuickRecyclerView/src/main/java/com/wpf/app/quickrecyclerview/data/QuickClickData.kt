package com.wpf.app.quickrecyclerview.data

import android.view.View
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quickbind.helper.binddatahelper.ItemClick
import com.wpf.app.quickbind.interfaces.itemClick

abstract class QuickClickData(
    override val layoutId: Int = 0,
    @Transient
    override val layoutView: View? = null,
    override val isSuspension: Boolean = false,
): QuickBindData() {

    @Transient
    @BindData2View(helper = ItemClick::class)
    private val itemClick = itemClick {
        onClick()
    }

    abstract fun onClick()
}