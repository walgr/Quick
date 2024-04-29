package com.wpf.app.quickrecyclerview.data

import android.view.View
import android.view.ViewGroup
import com.wpf.app.quick.annotations.bind.BindData2View
import com.wpf.app.quickbind.helper.binddatahelper.ItemClick
import com.wpf.app.quickutil.run.RunOnContextWithSelf
import com.wpf.app.quickutil.run.itemClick
import java.io.Serializable

/**
 * 监听了点击的Item
 */
abstract class QuickClickData @JvmOverloads constructor(
    layoutId: Int = 0,
    layoutViewCreate: RunOnContextWithSelf<ViewGroup, View>? = null,
    autoSet: Boolean = false,             //自动映射
    isSuspension: Boolean = false,
) : QuickBindData(
    layoutId = layoutId,
    layoutViewCreate = layoutViewCreate,
    autoSet = autoSet,
    isSuspension = isSuspension
), Serializable {

    @Transient
    @BindData2View(helper = ItemClick::class)
    var itemClick = itemClick {
        onClick()
    }

    abstract fun onClick()
}