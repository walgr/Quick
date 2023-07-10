package com.wpf.plugins

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.annotations.GetClass
import com.wpf.app.quick.annotations.GetFun
import com.wpf.plugins.processor.BindViewProcessor
import com.wpf.plugins.processor.GetClassProcessor

internal class QuickSymbolProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        QuickSymbolProcessor(environment)
}

internal class QuickSymbolProcessor(private val environment: SymbolProcessorEnvironment) :
    SymbolProcessor {

    private val dealClass = arrayOf(
        Pair(arrayOf(GetClass::class, GetFun::class), GetClassProcessor(environment)),
        Pair(arrayOf(BindView::class), BindViewProcessor(environment)),
    )

    override fun process(resolver: Resolver): List<KSAnnotated> {
        dealClass.map { annClassProcessor ->
            val symbols = annClassProcessor.first.flatMap {
                resolver.getSymbolsWithAnnotation(it.qualifiedName!!)
            }
            symbols.forEach {
                it.accept(annClassProcessor.second, Unit)
            }
        }
        return emptyList()
    }
}
