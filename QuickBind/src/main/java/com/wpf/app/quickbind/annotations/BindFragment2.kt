package com.wpf.app.quickbind.annotations

import com.wpf.app.quickbind.interfaces.BindBaseFragment
import kotlin.reflect.KClass

/**
 * Created by 王朋飞 on 2022/6/15.
 * 自动组装Fragment到ViewPager中
 * 自动复用Fragment
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindFragment2(
    val fragment: KClass<out BindBaseFragment>,
    val limit: Int = 0,
    val defaultSize: Int = 0
)
