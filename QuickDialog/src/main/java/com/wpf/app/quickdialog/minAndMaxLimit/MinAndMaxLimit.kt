package com.wpf.app.quickdialog.minAndMaxLimit

import android.view.View

interface MinAndMaxLimit {
    var maxWidth: Int
    var maxHeight: Int

    fun getFirstChild(): View?
}