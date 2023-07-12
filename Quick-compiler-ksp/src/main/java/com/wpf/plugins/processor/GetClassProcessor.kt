package com.wpf.plugins.processor

import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.wpf.app.quick.annotations.GetFun
import kotlin.reflect.KClass

class GetClassProcessor(environment: SymbolProcessorEnvironment) : BaseProcessor(environment) {

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        super.visitClassDeclaration(classDeclaration, data)
        val classAddName = "Class"
        outFileName = "$className$classAddName"
        if (outFileSpec == null) {
            outFileSpec = FileSpec.builder(packageName, outFileName!!)
        }
        outFileSpec?.addType(
                TypeSpec.objectBuilder(outFileName!!)
                    .addFunction(
                        FunSpec.builder("getJClass")
                            .returns(
                                Class::class.asClassName()
                                    .parameterizedBy(ClassName(packageName, className!!))
                            )
                            .addStatement(
                                "return %T::class.java", ClassName(packageName, className!!)
                            )
                            .build()
                    )
                    .addFunction(
                        FunSpec.builder("getKClass")
                            .returns(
                                KClass::class.asClassName()
                                    .parameterizedBy(ClassName(packageName, className!!))
                            )
                            .addStatement(
                                "return %T::class", ClassName(packageName, className!!)
                            )
                            .build()
                    )
                    .addFunctions(classDeclaration.getDeclaredFunctions().filter {
                        it.annotations.map { ann ->
                            ann.shortName.asString()
                        }.contains(GetFun::class.simpleName)
                    }.map {
                        FunSpec.builder(it.simpleName.getShortName())
                            .returns(String::class)
                            .addStatement("return %S", it.simpleName.getShortName())
                            .build()
                    }.asIterable())
                    .build()
            )
    }
}