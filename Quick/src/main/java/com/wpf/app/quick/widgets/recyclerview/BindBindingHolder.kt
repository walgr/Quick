package com.wpf.app.quick.widgets.recyclerview

import androidx.databinding.ViewDataBinding
import kotlin.reflect.KClass

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindBindingHolder(
    val value: KClass<out QuickViewBindingHolder<out QuickViewDataBinding<out ViewDataBinding>, out ViewDataBinding>>
)