package com.wpf.app.quick.widgets.recyclerview.data

import androidx.annotation.LayoutRes
import com.wpf.app.quick.widgets.recyclerview.data.QuickBindData

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickSelectData(
    open var id: String? = null,
    open var name: String? = null,
    open var isSelect: Boolean = false,
    open var defaultSelect: Boolean = false,        //是否默认选中，true清空后会再次选中
    @LayoutRes override val layoutId: Int,
) : QuickBindData(layoutId) {


}