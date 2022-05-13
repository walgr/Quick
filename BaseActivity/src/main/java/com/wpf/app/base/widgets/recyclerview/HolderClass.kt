package com.wpf.app.base.widgets.recyclerview

import kotlin.reflect.KClass

/**
 * Created by 王朋飞 on 2022/5/12.
 *
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class HolderClass(val holderClass: KClass<out CommonViewHolder<out CommonItemData>>)