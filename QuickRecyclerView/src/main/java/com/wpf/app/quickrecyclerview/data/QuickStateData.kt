package com.wpf.app.quickrecyclerview.data

import android.view.View
import android.view.ViewGroup
import com.wpf.app.quickutil.run.RunOnContextWithSelf
import java.io.Serializable

/**
 * 带状态切换的Item
 */
abstract class QuickStateData @JvmOverloads constructor(
    @Transient open var selected: Boolean = false,
    layoutId: Int = 0,
    layoutViewCreate: RunOnContextWithSelf<ViewGroup, View>? = null,
    autoSet: Boolean = false,             //自动映射
    isSuspension: Boolean = false,
) : QuickClickData(
    layoutId = layoutId,
    layoutViewCreate = layoutViewCreate,
    autoSet = autoSet,
    isSuspension = isSuspension
), Serializable {

    override fun onClick(view: View) {
        selected = !selected
        selectChange(selected)
    }

    abstract fun selectChange(selected: Boolean)
}