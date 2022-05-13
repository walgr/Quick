package com.wpf.app.base.widgets.recyclerview

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import kotlin.math.abs


/**
 * Created by 王朋飞 on 2022/5/11.
 * ViewHolder基础数据
 */
open class CommonItemDataBinding<VB : ViewDataBinding>(
    @LayoutRes val layoutId: Int = 0
) : CommonItemData(), LifecycleObserver {

    /**
     * 设置ViewBinding了
     */
    open fun <T : CommonItemDataBinding<VB>> onViewBindingSetT(viewHolder: CommonViewBindingHolder<T, VB>) {

    }

    open fun onViewBindingSet(viewHolder: CommonViewBindingHolder<out CommonItemDataBinding<VB>, VB>) {

    }
}