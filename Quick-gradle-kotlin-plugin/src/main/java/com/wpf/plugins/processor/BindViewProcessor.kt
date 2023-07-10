package com.wpf.plugins.processor

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration

class BindViewProcessor(private val environment: SymbolProcessorEnvironment) : BaseProcessor() {

    override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit) {
        super.visitPropertyDeclaration(property, data)
        environment.logger.warn("测试BindView" + "属性${property.simpleName.asString()}")
    }

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        super.visitClassDeclaration(classDeclaration, data)
        environment.logger.warn("测试BindView")
    }
}