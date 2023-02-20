package com.wpf.app.quickrecyclerview.data

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import com.wpf.app.quickrecyclerview.holder.QuickViewBindingHolder

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
@Deprecated("有复用问题，如果必须要用，请手动设置每项的viewType不同")
open class QuickViewDataBinding<VB : ViewDataBinding>(
    @LayoutRes open var layoutId: Int = 0,
    override var viewType: Int = 0
) : QuickItemData(viewType), LifecycleObserver {

    open fun onHolderCreated(viewHolder: QuickViewBindingHolder<out QuickViewDataBinding<VB>, VB>?) {}
}