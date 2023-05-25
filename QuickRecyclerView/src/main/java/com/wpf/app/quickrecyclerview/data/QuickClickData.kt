package com.wpf.app.quickrecyclerview.data

import android.view.View
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quickbind.helper.binddatahelper.ItemClick
import com.wpf.app.quickbind.interfaces.RunOnContext
import com.wpf.app.quickbind.interfaces.itemClick
import java.io.Serializable

abstract class QuickClickData @JvmOverloads constructor(
    override val layoutId: Int = 0,
    @Transient override val layoutView: View? = null,
    @Transient override val layoutViewInContext: RunOnContext<View>? = null,
    override val isSuspension: Boolean = false,
): QuickBindData(), Serializable {

    @Transient
    @BindData2View(helper = ItemClick::class)
    private val itemClick = itemClick {
        onClick()
    }

    abstract fun onClick()
}