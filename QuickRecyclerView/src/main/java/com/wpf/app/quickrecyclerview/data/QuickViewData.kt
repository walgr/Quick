package com.wpf.app.quickrecyclerview.data

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickutil.run.RunOnContextWithSelf

open class QuickViewData @JvmOverloads constructor(
    @LayoutRes open val layoutId: Int = 0,
    @Transient open var layoutViewInViewGroup: RunOnContextWithSelf<ViewGroup, View>? = null,
    isSuspension: Boolean = false       //View是否悬浮置顶
) : QuickSuspensionData(isSuspension = isSuspension) {
    open fun beforeAdapterCreateHolder(mParent: ViewGroup) {

    }

    open fun beforeHolderOnCreateHolder(holder: QuickViewHolder<QuickItemData>) {

    }

    open fun afterHolderOnCreateHolder(holder: QuickViewHolder<QuickItemData>) {

    }
}