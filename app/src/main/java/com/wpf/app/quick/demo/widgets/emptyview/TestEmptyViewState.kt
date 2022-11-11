package com.wpf.app.quick.demo.widgets.emptyview

import androidx.annotation.DrawableRes
import com.wpf.app.quickutil.widgets.emptyview.EmptyViewState

sealed class TestEmptyViewState(
    @DrawableRes val topImg: Int = 0,
    val title: String = "",
    val info: String = "",
    val btnStr: String = ""
): EmptyViewState()

