package com.wpf.app.quick.widgets.recyclerview

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickViewDataBinding<VB : ViewDataBinding>(
    @LayoutRes open var layoutId: Int = 0,
    override var viewType: Int = 0
) : QuickItemData(viewType), LifecycleObserver {

    open fun onHolderCreated(viewHolder: QuickViewBindingHolder<out QuickViewDataBinding<VB>, VB>?) {}
}