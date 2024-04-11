package com.wpf.app.quickutil.helper

import android.view.View
import android.view.Window

fun Window.contentView(): View? {
    return this.decorView.findViewById(android.R.id.content)
}