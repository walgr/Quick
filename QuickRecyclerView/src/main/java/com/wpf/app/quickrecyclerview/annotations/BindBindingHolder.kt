package com.wpf.app.quickrecyclerview.annotations

import androidx.databinding.ViewDataBinding
import com.wpf.app.quickrecyclerview.data.QuickViewDataBinding
import com.wpf.app.quickrecyclerview.holder.QuickViewBindingHolder
import kotlin.reflect.KClass

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
@Deprecated("未来废弃")
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindBindingHolder(
    val value: KClass<out QuickViewBindingHolder<out QuickViewDataBinding<out ViewDataBinding>, out ViewDataBinding>>
)