package com.wpf.app.quickbind.annotations

/**
 * Created by 王朋飞 on 2022/6/15.
 * 自动组装Fragment到ViewPager中
 * 自动复用Fragment
 */
@Target(AnnotationTarget.FIELD)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AutoGet(
    val value: String = ""
)
