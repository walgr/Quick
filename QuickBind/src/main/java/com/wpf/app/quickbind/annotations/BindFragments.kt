package com.wpf.app.quickbind.annotations

import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

/**
 * Created by 王朋飞 on 2022/6/15.
 * 自动组装Fragment到ViewPager中
 * 自动复用Fragment
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindFragments(
    val fragments: Array<KClass<out Fragment>>,
    val withState: Boolean = true,
    val limit: Int = 0,
)
