package com.wpf.plugins.processor

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName
import com.wpf.app.quick.annotations.request.GenerateCommonCall
import java.lang.reflect.ParameterizedType

class GenerateCommonCallProcessor(private val environment: SymbolProcessorEnvironment) :
    BaseProcessor(environment) {

    private val outFileSpecMap = mutableMapOf<String, FileSpec.Builder>()
    override fun visitClassDeclaration(
        classDeclaration: KSClassDeclaration,
        data: Unit,
        packageName: String,
        className: String
    ) {
        super.visitClassDeclaration(classDeclaration, data, packageName, className)
        println(classDeclaration)
        val generateNormalCallAnn = classDeclaration.annotations.find {
            it.shortName.getShortName() == GenerateCommonCall::class.simpleName
        } ?: return
        val generateClassName = generateNormalCallAnn.arguments.getOrNull(0)?.value as? String ?: ""
        val call = ClassName("retrofit2", "Call")
        val realCall = ClassName("com.wpf.app.quicknetwork.call", "RealCall")
        val responseClass = ClassName(packageName, className)
        val sResponse = TypeVariableName("SResponse")
        outFileSpecMap["call"] =
            FileSpec.builder(packageName, generateClassName.ifEmpty { className } + "Call")
                .addType(
                    TypeSpec.classBuilder(generateClassName.ifEmpty { className } + "Call")
                        .addModifiers(KModifier.OPEN)
                        .addTypeVariable(sResponse)
                        .primaryConstructor(
                            FunSpec.constructorBuilder()
                                .addParameter(
                                    "rawCall",
                                    call.parameterizedBy(responseClass.parameterizedBy(sResponse))
                                )
                                .addParameter(
                                    "fail",
                                    responseClass.parameterizedBy(Any::class.asClassName())
                                ).build()
                        ).superclass(
                            realCall.parameterizedBy(
                                responseClass.parameterizedBy(sResponse),
                                responseClass.parameterizedBy(Any::class.asClassName())
                            )
                        )
                        .addSuperclassConstructorParameter(CodeBlock.of("rawCall, fail")).build())

        val type = ClassName("java.lang.reflect", "Type")
        val cls = ClassName("java.lang", "Class")
        val callAdapter = ClassName("retrofit2", "CallAdapter")
        val commonCall =
            ClassName(packageName, generateClassName.ifEmpty { className } + "Call")
        outFileSpecMap["Adapter"] = FileSpec.builder(
            packageName,
            generateClassName.ifEmpty { className } + "CallAdapter")
            .addType(
                TypeSpec.classBuilder(generateClassName.ifEmpty { className } + "CallAdapter")
                    .addModifiers(KModifier.OPEN)
                    .addTypeVariable(sResponse)
                    .primaryConstructor(
                        FunSpec.constructorBuilder()
                            .addParameter(
                                "responseType",
                                type,
                            )
                            .addParameter(
                                "sResponse",
                                cls.parameterizedBy(sResponse)
                            ).build()
                    ).addProperty(
                        PropertySpec.builder("responseType", type)
                            .initializer("responseType")
                            .addModifiers(KModifier.PRIVATE)
                            .build()
                    ).addSuperinterface(
                        callAdapter.parameterizedBy(
                            responseClass.parameterizedBy(sResponse),
                            commonCall.parameterizedBy(sResponse)
                        )
                    ).addFunction(
                        FunSpec.builder("adapt")
                            .returns(commonCall.parameterizedBy(sResponse))
                            .addStatement("return %T(call, %T())", commonCall, responseClass)
                            .addModifiers(KModifier.OVERRIDE)
                            .addParameter(
                                "call",
                                call.parameterizedBy(responseClass.parameterizedBy(sResponse))
                            ).build()
                    ).addFunction(
                        FunSpec.builder("responseType")
                            .returns(type)
                            .addStatement("return responseType")
                            .addModifiers(KModifier.OVERRIDE)
                            .build()
                    )
                    .build()
            )

        val commonCallAdapter =
            ClassName(packageName, generateClassName.ifEmpty { className } + "CallAdapter")
        val commonCallAdapterFactory =
            ClassName(packageName, generateClassName.ifEmpty { className } + "CallAdapterFactory")
        outFileSpecMap["AdapterFactory"] = FileSpec.builder(packageName,
            generateClassName.ifEmpty { className } + "CallAdapterFactory")
            .addType(TypeSpec.classBuilder(generateClassName.ifEmpty { className } + "CallAdapterFactory")
                .addModifiers(KModifier.OPEN)
                .primaryConstructor(
                    FunSpec.constructorBuilder()
                        .addModifiers(KModifier.PRIVATE)
                        .build()
                )
                .superclass(callAdapter.nestedClass("Factory"))
                .addType(
                    TypeSpec.companionObjectBuilder()
                        .addFunction(
                            FunSpec.builder("create")
                                .returns(commonCallAdapterFactory)
                                .addStatement("return %T()", commonCallAdapterFactory)
                                .build()
                        ).build()
                )
                .addFunction(
                    FunSpec.builder("get")
                        .addModifiers(KModifier.OVERRIDE)
                        .addParameter("returnType", type)
                        .addParameter(
                            "annotations",
                            Array::class.asClassName()
                                .parameterizedBy(Annotation::class.asClassName())
                        )
                        .addParameter("retrofit", ClassName("retrofit2", "Retrofit"))
                        .returns(callAdapter.parameterizedBy(STAR, STAR).copy(nullable = true))
                        .addCode(
                            CodeBlock.of(
                                "val rawType = getRawType(returnType)\n" +
                                        "if (rawType == %T::class.java) {\n" +
                                        "   val sResponseType = getParameterUpperBound(0, returnType as %T)\n" +
                                        "   val sResponseClz = getRawType(sResponseType)\n" +
                                        "   return %T(sResponseType, sResponseClz)\n" +
                                        "}\n" +
                                        "return null",
                                commonCall,
                                ParameterizedType::class,
                                commonCallAdapter
                            )
                        )
                        .build()
                )
                .build())
    }

    override fun visitEnd() {
        if (outFileSpecMap.isEmpty()) return
        outFileSpecMap.values.forEach { outFileSpec ->
            environment.codeGenerator.createNewFile(
                Dependencies(true),
                packageName,
                outFileSpec.name,
            ).use {
                it.writer().use { writer ->
                    outFileSpec.build().writeTo(writer)
                }
            }
        }
    }
}