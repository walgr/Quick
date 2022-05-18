package com.wpf.app.quick.base.widgets.recyclerview

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver


/**
 * Created by 王朋飞 on 2022/5/11.
 * ViewHolder基础数据
 */

open class CommonItemDataBinding<VB : ViewDataBinding>(
    @LayoutRes val layoutId: Int = 0
) : CommonItemData(), LifecycleObserver {

    open fun onViewBindingSet(viewHolder: CommonViewBindingHolder<out CommonItemDataBinding<VB>, VB>) {

    }
}