package com.wpf.plugins.processor

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import java.io.File
import kotlin.reflect.KClass

open class IdProcessor(environment: SymbolProcessorEnvironment) : BaseProcessor(environment) {

    protected val srcFileStr by lazy {
        if (property?.containingFile == null) "" else File(property!!.containingFile!!.filePath).readText()
    }
    protected val fileStr by lazy {
        if (property?.containingFile == null) "" else File(property!!.containingFile!!.filePath).readText()
            .removeRN().trim()
    }

    protected fun getAnnotationArgumentIdCode(
        fileStr: String?,
        annotation: KClass<out Annotation>,
        type: String = "id"
    ): List<String> {
        return getAnnotationArgumentIdCode(
            getAnnotationCode(
                fileStr,
                annotation.simpleName!!,
            ), type
        ).map {
            delR(it)
        }
    }

    /**
     * 获取代码中注解内的代码内容
     */
    protected fun getAnnotationCode(
        fileStr: String?,
        startStr: String?,
    ): String {
        if (fileStr.isNullOrEmpty() || startStr.isNullOrEmpty()) return ""
        return fileStr.substringAfterAndBefore("@$startStr(", ")")
    }

    /**
     * 获取注解内参数的代码内容
     */
    private fun getAnnotationArgumentIdCode(
        annotationCode: String?,
        type: String = "id"
    ): List<String> {
        if (annotationCode.isNullOrEmpty()) return listOf()
        return annotationCode.split(",").filter {
            it.contains(".$type.")
        }.map {
            it.trim()
        }
    }

    protected fun delR(argumentsCode: String?): String {
        if (argumentsCode.isNullOrEmpty()) return ""
        return argumentsCode.substringAfterLast(".")
    }

    protected fun String.substringAfterAndBefore(after: String, before: String = ""): String {
        if (before.isEmpty()) return substringAfterLast(after)
        return substringAfterLast(after).substringBefore(before)
    }

    protected fun String.removeRN(): String {
        return replace("\r", "").replace("\n", "")
    }
}