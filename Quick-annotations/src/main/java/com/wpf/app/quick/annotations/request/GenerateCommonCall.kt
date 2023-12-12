package com.wpf.app.quick.annotations.request

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class GenerateCommonCall(
    val className: String = ""
)
