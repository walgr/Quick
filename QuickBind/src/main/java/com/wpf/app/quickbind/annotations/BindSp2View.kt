package com.wpf.app.quickbind.annotations

/**
 * Created by 王朋飞 on 2022/6/15.
 * 自动组装Fragment到ViewPager中
 * 自动复用Fragment
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindSp2View(
    val bindSp: String = "",
    val setSp: String = "",
    val getSp: String = "",
    val defaultValue: String = ""
)
