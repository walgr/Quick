package com.wpf.plugins.activity

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec

@Target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class GetClass


internal class GetClassProcessor(private val environment: SymbolProcessorEnvironment) :
    KSVisitorVoid() {

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        super.visitClassDeclaration(classDeclaration, data)
        val packageName = classDeclaration.containingFile?.packageName?.asString()
        val className = classDeclaration.simpleName.asString()
        if (packageName.isNullOrEmpty()) return
        val classAddName = "Class"
        val fileName = "$className$classAddName"
        val fileSpec = FileSpec.builder(packageName, fileName)
            .addType(
                TypeSpec.objectBuilder(fileName)
                    .addFunction(
                        FunSpec.builder("getJClass")
                            .returns(
                                ClassName("java.lang", "Class")
                                    .parameterizedBy(ClassName(packageName, className))
                            )
                            .addStatement(
                                "return %T::class.java", ClassName(packageName, className)
                            )
                            .build()
                    )
                    .addFunction(
                        FunSpec.builder("getKClass")
                            .returns(
                                ClassName("kotlin.reflect", "KClass")
                                    .parameterizedBy(ClassName(packageName, className))
                            )
                            .addStatement(
                                "return %T::class", ClassName(packageName, className)
                            )
                            .build()
                    )
                    .build()
            )
            .build()
        environment.codeGenerator.createNewFile(
            if (classDeclaration.containingFile == null) Dependencies(true) else Dependencies(
                true,
                classDeclaration.containingFile!!
            ),
            packageName,
            fileName,
            extensionName = "kt"
        ).use {
            it.writer().use { writer ->
                fileSpec.writeTo(writer)
            }
        }
    }
}