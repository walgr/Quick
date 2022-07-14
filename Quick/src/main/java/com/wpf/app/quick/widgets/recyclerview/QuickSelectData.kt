package com.wpf.app.quick.widgets.recyclerview

import androidx.annotation.LayoutRes

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickSelectData(
    open val id: String? = null,
    open val isSelect: Boolean = false,
    @LayoutRes override var layoutId: Int = 0,
    override var viewType: Int = 0
) : QuickBindData(layoutId, viewType)