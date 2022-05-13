package com.wpf.app.base.widgets.recyclerview

import androidx.annotation.LayoutRes

/**
 * Created by 王朋飞 on 2022/5/12.
 *
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class HolderBindingLayout(@LayoutRes val layout: Int)