package com.wpf.app.quickrecyclerview.helper

import android.view.View
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.listeners.QuickAdapterListener

fun <T: QuickItemData> itemClick(click: (view: View, data: T?, position: Int) -> Unit) = object :
    QuickAdapterListener<T> {
    override fun onItemClick(view: View, data: T?, position: Int) {
        click.invoke(view, data, position)
    }
}