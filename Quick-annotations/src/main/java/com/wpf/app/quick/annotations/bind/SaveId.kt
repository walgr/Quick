package com.wpf.app.quick.annotations.bind

import androidx.annotation.IdRes

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class SaveId(@IdRes val value: Int)