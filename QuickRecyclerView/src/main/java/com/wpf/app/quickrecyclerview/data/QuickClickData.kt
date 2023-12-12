package com.wpf.app.quickrecyclerview.data

import android.view.View
import android.view.ViewGroup
import com.wpf.app.quick.annotations.bind.BindData2View
import com.wpf.app.quickbind.helper.binddatahelper.ItemClick
import com.wpf.app.quickutil.bind.RunOnContextWithSelf
import com.wpf.app.quickutil.bind.itemClick
import java.io.Serializable

/**
 * 监听了点击的Item
 */
abstract class QuickClickData @JvmOverloads constructor(
    @Transient override val layoutId: Int = 0,
    @Transient override val layoutViewInContext: RunOnContextWithSelf<ViewGroup, View>? = null,
    @Transient override val isSuspension: Boolean = false,
    @Transient override val isDealBinding: Boolean = false,      //是否处理DataBinding
    @Transient override val autoSet: Boolean = false             //自动映射
) : QuickBindData(), Serializable {

    @Transient
    @BindData2View(helper = ItemClick::class)
    private val itemClick = itemClick {
        onClick()
    }

    abstract fun onClick()
}