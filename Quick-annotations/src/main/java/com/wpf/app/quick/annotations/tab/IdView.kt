package com.wpf.app.quick.annotations.tab

import androidx.annotation.IdRes
import com.wpf.app.quick.annotations.tab.view.ViewType

annotation class IdView(
    @IdRes
    val id: Int,
    val viewClass: ViewType
)
