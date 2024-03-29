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
    layoutId: Int = 0,
    layoutViewInContext: RunOnContextWithSelf<ViewGroup, View>? = null,
    isDealBinding: Boolean = false,      //是否处理DataBinding
    autoSet: Boolean = false,             //自动映射
    isSuspension: Boolean = false,
) : QuickBindData(
    layoutId = layoutId,
    layoutViewInContext = layoutViewInContext,
    isDealBinding = isDealBinding,
    autoSet = autoSet,
    isSuspension = isSuspension
), Serializable {

    @Transient
    @BindData2View(helper = ItemClick::class)
    private val itemClick = itemClick {
        onClick()
    }

    abstract fun onClick()
}