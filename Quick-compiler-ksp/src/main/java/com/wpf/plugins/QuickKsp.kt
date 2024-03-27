package com.wpf.plugins

import com.google.devtools.ksp.containingFile
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.wpf.app.quick.annotations.tab.TabInit
import com.wpf.app.quick.annotations.getclass.GetClass
import com.wpf.app.quick.annotations.getclass.GetFun
import com.wpf.app.quick.annotations.request.GenerateCommonCall
import com.wpf.app.quick.annotations.request.GenerateNormalCall
import com.wpf.app.quick.annotations.request.GenerateRequest
import com.wpf.plugins.processor.BaseProcessor
import com.wpf.plugins.processor.BindViewProcessor
import com.wpf.plugins.processor.GenerateCommonCallProcessor
import com.wpf.plugins.processor.GenerateNormalCallProcessor
import com.wpf.plugins.processor.GenerateRequestProcessor
import com.wpf.plugins.processor.GetClassProcessor
import com.wpf.plugins.processor.TabInitProcessor
import kotlin.reflect.full.primaryConstructor

internal class QuickSymbolProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        QuickSymbolProcessor(environment)
}

internal class QuickSymbolProcessor(private val environment: SymbolProcessorEnvironment) :
    SymbolProcessor {

    private val dealClass = arrayOf(
        Pair(BindViewProcessor.allowClass, BindViewProcessor::class),
        Pair(arrayOf(GetClass::class, GetFun::class), GetClassProcessor::class),
        Pair(arrayOf(TabInit::class), TabInitProcessor::class),
        Pair(arrayOf(GenerateNormalCall::class), GenerateNormalCallProcessor::class),
        Pair(arrayOf(GenerateCommonCall::class), GenerateCommonCallProcessor::class),
        Pair(arrayOf(GenerateRequest::class), GenerateRequestProcessor::class),
    )

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val processorMap = mutableMapOf<String, BaseProcessor>()
        dealClass.forEach { annClassProcessor ->
            if (annClassProcessor.first.first() == TabInit::class) {
                val symbols = annClassProcessor.first.flatMap {
                    resolver.getSymbolsWithAnnotation(it.qualifiedName!!)
                }
                if (symbols.isEmpty()) {
                    return@forEach
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
                        val processor = annClassProcessor.second.primaryConstructor!!.call(environment)
                        symbol.accept(processor, Unit)
                        processor.visitEnd()
                    }
                }
            } else {
                val symbols = annClassProcessor.first.flatMap {
                    resolver.getSymbolsWithAnnotation(it.qualifiedName!!)
                }
                if (symbols.isEmpty()) {
                    return@forEach
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
                        var processor = processorMap[file + annClassProcessor.second.simpleName]
                        if (processor == null) {
                            processor =
                                annClassProcessor.second.primaryConstructor!!.call(environment)
                            processorMap[file + annClassProcessor.second.simpleName] = processor
                        }
                        symbol.accept(processor, Unit)
                        if (index == it.second.size - 1) {
                            processor.visitEnd()
                        }
                    }
                }
            }
        }
        return emptyList()
    }

    private fun print(msg: String) {
        environment.logger.warn(msg)
    }
}
