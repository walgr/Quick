package com.wpf.app.quickwidget.quickview.bind

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
    override var addToParent: Boolean = true,
    open val id: String? = null,
    open val isSelect: Boolean = false,
    @LayoutRes val layoutId: Int,
) : QuickBindGroup<T>(
    mContext,
    attributeSet,
    defStyleAttr,
    addToParent = addToParent,
    layoutId = layoutId
)