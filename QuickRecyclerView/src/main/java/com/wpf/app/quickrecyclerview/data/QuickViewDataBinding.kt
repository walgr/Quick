package com.wpf.app.quickrecyclerview.data

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver

/**
 * Created by 王朋飞 on 2022/7/13.
 * 支持DataBinding的Item
 */
open class QuickViewDataBinding<VB : ViewDataBinding>(
    layoutId: Int,
) : QuickViewData(layoutId = layoutId), LifecycleObserver {

//    open fun onHolderCreated(viewHolder: QuickViewBindingHolder<out QuickViewDataBinding<VB>, VB>?) {}
}