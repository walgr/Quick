package com.wpf.app.quickrecyclerview.data

import android.view.View
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quickbind.helper.binddatahelper.ItemClick
import com.wpf.app.quickbind.interfaces.RunOnContext
import com.wpf.app.quickbind.interfaces.itemClick
import java.io.Serializable

abstract class QuickClickData @JvmOverloads constructor(
    @Transient override val layoutId: Int = 0,
    @Transient override val layoutView: View? = null,
    @Transient override val layoutViewInContext: RunOnContext<View>? = null,
    @Transient override val isSuspension: Boolean = false,
    @Transient override val isDealBinding: Boolean = false,      //是否处理DataBinding
    @Transient override val autoSet: Boolean = false             //自动映射
) : QuickBindData(isSuspension = isSuspension, isDealBinding = isDealBinding, autoSet = autoSet),
    Serializable {

    @Transient
    @BindData2View(helper = ItemClick::class)
    private val itemClick = itemClick {
        onClick()
    }

    abstract fun onClick()
}