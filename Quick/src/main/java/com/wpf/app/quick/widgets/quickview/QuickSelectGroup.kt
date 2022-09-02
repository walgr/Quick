package com.wpf.app.quick.widgets.quickview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickSelectGroup<T : ViewGroup> @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    open val id: String? = null,
    open val isSelect: Boolean = false,
    @LayoutRes val layoutId: Int,
) : QuickBindGroup<T>(mContext, attributeSet, defStyleAttr, layoutId)