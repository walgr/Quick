package com.wpf.app.quick.annotations.tab

import androidx.annotation.IdRes
import com.wpf.app.quick.annotations.tab.view.ViewType

annotation class IdView(
    @IdRes
    val id: Int,
    @Suppress("unused")
    val viewClass: ViewType = ViewType.CustomView,
    val className: String = ""
)
