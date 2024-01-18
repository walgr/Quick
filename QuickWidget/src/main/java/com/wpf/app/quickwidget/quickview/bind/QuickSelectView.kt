package com.wpf.app.quickwidget.quickview.bind

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.LayoutRes

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickSelectView @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    open val id: String? = null,
    open val isSelect: Boolean = false,
    @LayoutRes val layoutId: Int,
) : QuickBindView(mContext, attributeSet, defStyleAttr, layoutId)