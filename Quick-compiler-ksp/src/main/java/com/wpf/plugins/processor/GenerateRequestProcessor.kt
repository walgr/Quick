package com.wpf.plugins.processor

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeVariableName
import com.wpf.app.quick.annotations.request.GenerateRequest

class GenerateRequestProcessor(environment: SymbolProcessorEnvironment) :
    BaseProcessor(environment) {
    private val outFunSpecMap = mutableMapOf<String, FunSpec.Builder>()
    override fun visitClassDeclaration(
        classDeclaration: KSClassDeclaration,
        data: Unit,
        packageName: String,
        className: String,
    ) {
        super.visitClassDeclaration(classDeclaration, data, packageName, className)
        val generateRequestAnn = classDeclaration.annotations.find {
            it.shortName.getShortName().contains(GenerateRequest::class.simpleName!!)
        } ?: return
        val fileName = generateRequestAnn.arguments.getOrNull(0)?.value as? String
        val funName = generateRequestAnn.arguments.getOrNull(1)?.value as String
        val registerService = generateRequestAnn.arguments.getOrNull(2)?.value as Boolean
        val baseUrl = generateRequestAnn.arguments.getOrNull(3)?.value as String
        outFileName = fileName

        val dataType = TypeVariableName("Data")
        val failType = TypeVariableName("Fail")
        val api = ClassName(packageName, className)
        val quick = ClassName("com.wpf.app.quickutil", "Quick")
        val baseRequest = ClassName("com.wpf.app.quicknetwork.base", "BaseRequest")
        val retrofitCreateHelper =
            ClassName("com.wpf.app.quicknetwork.helper", "RetrofitCreateHelper")
        val wpfRequest = ClassName("com.wpf.app.quicknetwork.base", "WpfRequest")
        val realCall = ClassName("com.wpf.app.quicknetwork.call", "RealCall")
        val requestCoroutineScope =
            ClassName("com.wpf.app.quicknetwork.base", "RequestCoroutineScope")

        val defaultFunBuilder = FunSpec.builder(funName)
            .addModifiers(KModifier.INLINE)
            .addTypeVariable(dataType)
            .addTypeVariable(failType)
            .addParameter(
                ParameterSpec.builder("context", requestCoroutineScope.copy(nullable = true))
                    .defaultValue("null").build()
            ).addParameter(
                ParameterSpec.builder(
                    "run",
                    LambdaTypeName.get(api, listOf(), realCall.parameterizedBy(dataType, failType))
                ).build()
            ).returns(
                baseRequest.parameterizedBy(dataType, failType)
            )
        if (registerService) {
            defaultFunBuilder.addCode(
                CodeBlock.of(
                    "%T.registerServices(%T::class.java" + if (baseUrl.isNotEmpty()) ", ${baseUrl})" else ")" + "\n",
                    retrofitCreateHelper,
                    api
                )
            )
        }
        defaultFunBuilder.addCode(
            CodeBlock.of(
                "return %T.getService<%T>().run(run).enqueue(%T(context))",
                retrofitCreateHelper,
                api,
                wpfRequest
            )
        )
        outFunSpecMap["default"] = defaultFunBuilder
        val quickFunBuilder = FunSpec.builder(funName)
            .addModifiers(KModifier.INLINE)
            .addTypeVariable(dataType)
            .addTypeVariable(failType)
            .receiver(quick)
            .addParameter(
                ParameterSpec.builder(
                    "run",
                    LambdaTypeName.get(api, listOf(), realCall.parameterizedBy(dataType, failType))
                ).build()
            ).returns(
                baseRequest.parameterizedBy(dataType, failType)
            )
        if (registerService) {
            quickFunBuilder.addCode(
                CodeBlock.of(
                    "%T.registerServices(%T::class.java" + if (baseUrl.isNotEmpty()) ", ${baseUrl})" else ")" + "\n",
                    retrofitCreateHelper,
                    api
                )
            )
        }
        quickFunBuilder.addCode(
            CodeBlock.of(
                "return %T.getService<%T>().run(run).enqueue(%T(this as? RequestCoroutineScope))",
                retrofitCreateHelper,
                api,
                wpfRequest
            )
        )
        outFunSpecMap["quick"] = quickFunBuilder
    }

    override fun visitEnd() {
        if (outFileName.isNullOrEmpty() ||outFunSpecMap.isEmpty()) return
        if (outFileSpec == null) {
            outFileSpec = FileSpec.builder(packageName, outFileName!!)
        }
        outFunSpecMap.values.forEach { funBuilder ->
            outFileSpec?.addFunction(funBuilder.build())
        }
        super.visitEnd()

    }
}