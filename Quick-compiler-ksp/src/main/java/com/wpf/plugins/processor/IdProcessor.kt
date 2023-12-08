package com.wpf.plugins.processor

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import java.io.File
import kotlin.reflect.KClass

open class IdProcessor(environment: SymbolProcessorEnvironment) : BaseProcessor(environment) {
    protected val fileStr by lazy {
        if (property?.containingFile == null) "" else File(property!!.containingFile!!.filePath).readText()
            .replace("\r", "").replace("\n", "").trim()
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
                propertyName!!
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
        annotationName: String?,
        propertyName: String
    ): String {
        if (fileStr.isNullOrEmpty() || annotationName.isNullOrEmpty()) return ""
        return fileStr.substringAfterAndBefore("@$annotationName", propertyName)
            .substringAfterAndBeforeLast("(", ")")
    }

    /**
     * 获取注解内参数的代码内容
     */
    protected fun getAnnotationArgumentIdCode(
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
        if (before.isEmpty()) return substringAfter(after)
        return substringAfter(after).substringBefore(before)
    }

    protected fun String.substringAfterAndBeforeNoInclude(
        after: String,
        before: String = ""
    ): String {
        if (before.isEmpty()) return substringAfter(after).replace(after, "")
        return substringAfter(after).replace(after, "").substringBefore(before).replace(before, "")
    }

    protected fun String.substringAfterAndBeforeLast(after: String, before: String = ""): String {
        if (before.isEmpty()) return substringAfter(after)
        return substringAfter(after).substringBeforeLast(before)
    }

    protected fun String.substringAfterAndBeforeLastNoInclude(
        after: String,
        before: String = ""
    ): String {
        if (before.isEmpty()) return substringAfter(after).replace(after, "")
        return substringAfter(after).replace(after, "").substringBeforeLast(before)
            .replace(before, "")
    }
}