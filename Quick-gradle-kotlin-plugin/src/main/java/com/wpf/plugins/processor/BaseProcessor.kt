package com.wpf.plugins.processor

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid

open class BaseProcessor : KSVisitorVoid() {
    open lateinit var packageName: String
    open lateinit var className: String

    override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit) {
        super.visitPropertyDeclaration(property, data)
        packageName = property.containingFile?.packageName?.asString() ?: ""
        className = property.simpleName.asString()
        if (packageName.isEmpty()) return
    }

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        super.visitClassDeclaration(classDeclaration, data)
        packageName = classDeclaration.containingFile?.packageName?.asString() ?: ""
        className = classDeclaration.simpleName.asString()
        if (packageName.isEmpty()) return
    }
}