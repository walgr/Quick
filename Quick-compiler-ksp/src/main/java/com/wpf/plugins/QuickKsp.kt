package com.wpf.plugins

import com.google.devtools.ksp.containingFile
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.annotations.GetClass
import com.wpf.app.quick.annotations.GetFun
import com.wpf.app.quick.annotations.GroupView
import com.wpf.app.quick.annotations.SaveId
import com.wpf.plugins.processor.BaseProcessor
import com.wpf.plugins.processor.BindViewProcessor
import com.wpf.plugins.processor.GetClassProcessor
import kotlin.reflect.full.primaryConstructor

internal class QuickSymbolProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        QuickSymbolProcessor(environment)
}

internal class QuickSymbolProcessor(private val environment: SymbolProcessorEnvironment) :
    SymbolProcessor {

    private val dealClass = arrayOf(
        Pair(arrayOf(GetClass::class, GetFun::class), GetClassProcessor::class),
        Pair(BindViewProcessor.allowClass, BindViewProcessor::class),
    )

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val processorMap = mutableMapOf<String, BaseProcessor>()
        dealClass.forEach { annClassProcessor ->
            val symbols = annClassProcessor.first.flatMap {
                resolver.getSymbolsWithAnnotation(it.qualifiedName!!)
            }
            val symbolMap = mutableListOf<Pair<String, MutableList<KSAnnotated>>>()
            symbols.map { symbol ->
                val file = "${symbol.containingFile?.filePath}"
                val ksAnnotatedList = symbolMap.find {
                    it.first == file
                }?.second
                ksAnnotatedList?.add(symbol) ?: symbolMap.add(Pair(file, mutableListOf(symbol)))
            }
            symbolMap.forEach {
                it.second.forEachIndexed { index, symbol ->
                    val file = "${symbol.containingFile?.filePath}"
                    var processor = processorMap[file]
                    if (processor == null) {
                        processor = annClassProcessor.second.primaryConstructor!!.call(environment)
                        processorMap[file] = processor
                    }
                    symbol.accept(processor, Unit)
                    if (index == it.second.size - 1) {
                        processor.visitEnd()
                    }
                }
            }
        }
        return emptyList()
    }
}