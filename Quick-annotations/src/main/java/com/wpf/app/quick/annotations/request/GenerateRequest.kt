package com.wpf.app.quick.annotations.request

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class GenerateRequest(
    val fileName: String,
    val funName: String = "request",
    val registerService: Boolean = true,
    val baseUrl: String = "",
)