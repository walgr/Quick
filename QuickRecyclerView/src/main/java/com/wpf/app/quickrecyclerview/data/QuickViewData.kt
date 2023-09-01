package com.wpf.app.quickrecyclerview.data

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.wpf.app.quickutil.bind.RunOnContextWithSelf

open class QuickViewData @JvmOverloads constructor(
    @LayoutRes open val layoutId: Int = 0,
    @Transient open val layoutViewInContext: RunOnContextWithSelf<View, ViewGroup>? = null,
    @Transient override val isSuspension: Boolean = false       //View是否悬浮置顶
) : QuickSuspensionData()