package com.wpf.app.quickrecyclerview.helper

import android.view.View
import com.wpf.app.quickrecyclerview.data.QuickFooterData
import com.wpf.app.quickrecyclerview.data.QuickHeaderData
import com.wpf.app.quickrecyclerview.data.QuickViewData

fun View.toHeaderViewData(isSuspension: Boolean = false, isMatch: Boolean = true): QuickHeaderData {
    return QuickHeaderData(isSuspension = isSuspension, isMatch = isMatch).apply {
        layoutView = this@toHeaderViewData
        viewType = this.hashCode()
    }
}

fun View.toFooterViewData(isMatch: Boolean = true): QuickFooterData {
    return QuickFooterData(isMatch = isMatch).apply {
        layoutView = this@toFooterViewData
        viewType = this.hashCode()
    }
}

fun View.toViewData(isSuspension: Boolean = false): QuickViewData {
    return QuickViewData(layoutView = this, isSuspension = isSuspension).apply {
        viewType = this.hashCode()
    }
}