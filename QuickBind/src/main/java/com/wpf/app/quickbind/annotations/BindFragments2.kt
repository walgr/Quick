package com.wpf.app.quickbind.annotations

import com.wpf.app.quickbind.interfaces.BindBaseFragment
import kotlin.reflect.KClass

/**
 * Created by 王朋飞 on 2022/10/7.
 * 自动组装Fragment到ViewPager2中
 * 自动复用Fragment
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindFragments2(
    val fragments: Array<KClass<out BindBaseFragment>>,
    val limit: Int = 0,
)