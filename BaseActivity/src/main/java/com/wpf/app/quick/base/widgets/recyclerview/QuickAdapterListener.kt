package com.wpf.app.quick.base.widgets.recyclerview

import android.view.View

/**
 * Created by 王朋飞 on 2022/5/13.
 *
 */
interface QuickAdapterListener<T: QuickItemData> {
    fun onItemClick(view: View, data: T?, position: Int)
}