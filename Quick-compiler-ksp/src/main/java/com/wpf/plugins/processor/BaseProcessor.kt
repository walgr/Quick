package com.wpf.plugins.processor

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.FileSpec
import java.io.OutputStream

open class BaseProcessor(private val environment: SymbolProcessorEnvironment) : KSVisitorVoid() {
    open lateinit var packageName: String       //包名
    open var className: String? = null         //类名
    open var propertyName: String? = null      //属性名称

    var outFileName: String? = null
    var outFileSpec: FileSpec.Builder? = null

    open fun visitEnd() {
        if (outFileName.isNullOrEmpty()) return
        environment.codeGenerator.createNewFile(
            Dependencies(true),
            packageName,
            outFileName!!,
        ).use {
            it.writer().use { writer ->
                outFileSpec?.build()?.writeTo(writer)
            }
        }
    }

    override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit) {
        super.visitPropertyDeclaration(property, data)
        getClassData(property)
        className = property.parentDeclaration?.simpleName?.getShortName()
        if (packageName.isEmpty() || className.isNullOrEmpty()) return
        propertyName = property.simpleName.asString()
        visitPropertyDeclaration(property, data, packageName, className!!, propertyName)
    }

    open fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit, packageName: String, className: String, propertyName: String?) {

    }

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        super.visitClassDeclaration(classDeclaration, data)
        getClassData(classDeclaration)
        className = classDeclaration.simpleName.asString()
        if (packageName.isEmpty() || className.isNullOrEmpty()) return
        visitClassDeclaration(classDeclaration, data, packageName, className!!)
    }

    open fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit, packageName: String, className: String) {

    }

    fun println(msg: String) {
        environment.logger.warn(msg)
    }

    private fun getClassData(declaration: KSDeclaration) {
        packageName = declaration.packageName.asString()
    }
}