package com.wpf.app.quick.annotations

import androidx.annotation.LayoutRes

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class TabInit(
    @LayoutRes
    val layoutId: Int,
    val funName: String = "",
    val initIdList: Array<IdView> = []
)