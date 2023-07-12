package com.wpf.app.quick.annotations

import androidx.annotation.IdRes

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class SaveId(@IdRes val value: Int)