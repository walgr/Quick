package com.wpf.app.quick.annotations

import androidx.annotation.IdRes
import com.wpf.app.quick.annotations.view.ViewType

annotation class IdView(
    @IdRes
    val id: Int,
    val viewClass: ViewType
)
