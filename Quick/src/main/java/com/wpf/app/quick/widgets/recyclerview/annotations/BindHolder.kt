package com.wpf.app.quick.widgets.recyclerview.annotations

import com.wpf.app.quick.widgets.recyclerview.data.QuickItemData
import com.wpf.app.quick.widgets.recyclerview.holder.QuickViewHolder
import kotlin.reflect.KClass

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindHolder(
    val value: KClass<out QuickViewHolder<out QuickItemData>>
)
