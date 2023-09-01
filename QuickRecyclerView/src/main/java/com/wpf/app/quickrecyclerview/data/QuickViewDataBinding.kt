package com.wpf.app.quickrecyclerview.data

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import com.wpf.app.quickrecyclerview.holder.QuickViewBindingHolder

/**
 * Created by 王朋飞 on 2022/7/13.
 * 支持DataBinding的Item
 */
open class QuickViewDataBinding<VB : ViewDataBinding> @JvmOverloads constructor(
    @Transient @LayoutRes open var layoutId: Int = 0,
) : QuickItemData(), LifecycleObserver {

    open fun onHolderCreated(viewHolder: QuickViewBindingHolder<out QuickViewDataBinding<VB>, VB>?) {}
}