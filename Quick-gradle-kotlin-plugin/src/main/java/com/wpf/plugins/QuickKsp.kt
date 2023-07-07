package com.wpf.plugins

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wpf.plugins.activity.GetClass
import com.wpf.plugins.activity.GetClassProcessor

internal class QuickSymbolProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        QuickSymbolProcessor(environment)
}

internal class QuickSymbolProcessor(private val environment: SymbolProcessorEnvironment) :
    SymbolProcessor {
    private val dealClass = arrayOf(GetClass::class.qualifiedName!!)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        dealClass.map { annClass ->
            val symbols = resolver.getSymbolsWithAnnotation(annClass).filterIsInstance<KSClassDeclaration>()
            symbols.forEach {
                it.accept(GetClassProcessor(environment), Unit)
            }
        }
        return emptyList()
    }
}
