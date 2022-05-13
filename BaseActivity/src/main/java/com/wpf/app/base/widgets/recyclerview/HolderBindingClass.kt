package com.wpf.app.base.widgets.recyclerview

import androidx.databinding.ViewDataBinding
import kotlin.reflect.KClass

/**
 * Created by 王朋飞 on 2022/5/12.
 *
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class HolderBindingClass(
    val holderClass: KClass<out CommonViewBindingHolder<out CommonItemDataBinding<out ViewDataBinding>, out ViewDataBinding>>
)