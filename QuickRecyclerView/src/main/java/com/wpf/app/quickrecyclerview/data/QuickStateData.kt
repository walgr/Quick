package com.wpf.app.quickrecyclerview.data

import android.view.View
import android.view.ViewGroup
import com.wpf.app.quickutil.bind.RunOnContextWithSelf
import java.io.Serializable

/**
 * 带状态切换的Item
 */
abstract class QuickStateData @JvmOverloads constructor(
    @Transient open var state: Boolean = false,
    @Transient override val layoutId: Int = 0,
    @Transient override val layoutViewInContext: RunOnContextWithSelf<ViewGroup, View>? = null,
    @Transient override val isSuspension: Boolean = false,
    @Transient override val isDealBinding: Boolean = false,      //是否处理DataBinding
    @Transient override val autoSet: Boolean = false             //自动映射
) : QuickClickData(), Serializable {

    override fun onClick() {
        state = !state
        stateChange(state)
    }

    abstract fun stateChange(state: Boolean)
}