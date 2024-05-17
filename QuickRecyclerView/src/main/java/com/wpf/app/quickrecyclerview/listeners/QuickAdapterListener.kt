package com.wpf.app.quickrecyclerview.listeners

import android.view.View

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
interface QuickAdapterListener<T> {
    fun onItemClick(view: View, data: T?, position: Int)
}