package com.wpf.app.quickrecyclerview.helper

import android.view.View
import com.wpf.app.quickrecyclerview.data.QuickViewData

fun View.toViewData(isSuspension: Boolean = false): QuickViewData {
    return QuickViewData(layoutView = this, isSuspension = isSuspension).apply {
        viewType = this.hashCode()
    }
}