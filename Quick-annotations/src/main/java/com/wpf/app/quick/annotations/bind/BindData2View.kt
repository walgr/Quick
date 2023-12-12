package com.wpf.app.quick.annotations.bind

import androidx.annotation.IdRes
import kotlin.reflect.KClass

/**
 * Created by 王朋飞 on 2022/7/5.
 * 如果不传id 代表的是整体的view
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindData2View(
    @IdRes val id: Int = 0,
    val helper: KClass<out BindD2VHHelper<*, *, *>> = BindD2VHHelper::class
)