package com.wpf.app.quickbind.annotations

/**
 * Created by 王朋飞 on 2022/6/15.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class LoadSp(
    val fileName: String = "QuickViewSpBindFile",
    val key: String,
    val defaultValue: String = ""
)
