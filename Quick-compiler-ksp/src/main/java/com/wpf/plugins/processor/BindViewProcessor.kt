package com.wpf.plugins.processor

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.annotations.Databinder
import com.wpf.app.quick.annotations.GroupView
import com.wpf.app.quick.annotations.SaveId
import java.io.File
import kotlin.reflect.KClass

class BindViewProcessor(environment: SymbolProcessorEnvironment) :
    BaseProcessor(environment) {

    companion object {
        val allowClass =
            arrayOf(BindView::class, BindData2View::class, SaveId::class, GroupView::class)
    }

    private val outFileStartName = "Quick_"
    private val outFileEndName = "_ViewBinding_Ksp"
    private val bindData2ViewEndName = "BindViewId"

    private var findViewFunBuilder: FunSpec.Builder? = null
    private var classTypeBuilder: TypeSpec.Builder? = null

    override fun visitPropertyDeclaration(
        property: KSPropertyDeclaration,
        data: Unit,
        packageName: String,
        className: String,
        propertyName: String?
    ) {
        super.visitPropertyDeclaration(property, data, packageName, className, propertyName)
        val fileStr =
            if (property.containingFile == null) "" else File(property.containingFile!!.filePath).readText()
        //过滤非R2的
        if (!getAnnotationCode(
                fileStr,
                property.annotations.find {
                    allowClass.map { cls -> cls.simpleName }.contains(it.shortName.asString())
                }!!.shortName.asString(),
                propertyName!!
            ).contains("R2")
        ) return
        outFileName = outFileStartName + className + outFileEndName
        if (findViewFunBuilder == null) {
            findViewFunBuilder = FunSpec.constructorBuilder()
                .addParameter("target", ClassName(packageName, className))
                .addParameter("source", ClassName("android.view", "View"))
        }
        if (classTypeBuilder == null) {
            classTypeBuilder = TypeSpec.classBuilder(outFileName!!)
                .addModifiers(KModifier.FINAL)
                .addSuperinterface(Databinder::class)
            classTypeBuilder?.addFunction(
                FunSpec.constructorBuilder()
                    .addParameter("target", ClassName(packageName, className))
                    .addStatement(
                        "${outFileName}(target, (target as %T).getView()!!)",
                        ClassName("com.wpf.app.quickutil.bind", "Bind")
                    )
                    .build()
            )
        }


        property.annotations.find {
            it.shortName.asString() == BindData2View::class.simpleName
        }?.let {
            classTypeBuilder?.addProperty(
                PropertySpec.builder(propertyName + bindData2ViewEndName, Int::class)
                    .mutable()
                    .initializer("0")
                    .build()
            )
            val annotationArgumentCode =
                getAnnotationArgumentIdCode(fileStr, BindData2View::class, BindData2View::id.name)
            findViewFunBuilder?.addStatement("this.${propertyName + bindData2ViewEndName} = R.id.${annotationArgumentCode}")
        }
        property.annotations.find {
            it.shortName.asString() == GroupView::class.simpleName
        }?.let {
            classTypeBuilder?.addProperty(
                PropertySpec.builder(
                    "mGroupViews",
                    ClassName(
                        "kotlin.collections",
                        "MutableList"
                    ).parameterizedBy(Int::class.asClassName())
                )
                    .initializer("mutableListOf<Int>()")
                    .build()
            )
            getAnnotationCode(
                fileStr,
                GroupView::class.simpleName!!,
                propertyName
            ).substringAfterAndBefore("[", "]")
                .split(",").forEach {
                    val code = delR(it)
                    findViewFunBuilder?.addStatement("mGroupViews.add(R.id.${code})")
                }
        }

        property.annotations.find {
            it.shortName.asString() == BindView::class.simpleName
        }?.let {
            findViewFunBuilder?.addStatement("target.${propertyName} = source.findViewById(R.id.${propertyName})")
        }
    }

    private fun getAnnotationArgumentIdCode(
        fileStr: String?,
        annotation: KClass<out Annotation>,
        argumentName: String
    ): String {
        return delR(
            getAnnotationArgumentIdCode(
                getAnnotationCode(
                    fileStr,
                    annotation.simpleName!!,
                    propertyName!!
                ), argumentName
            )
        )
    }

    /**
     * 获取代码中注解内的代码内容
     */
    private fun getAnnotationCode(
        fileStr: String?,
        annotationName: String,
        propertyName: String
    ): String {
        if (fileStr.isNullOrEmpty()) return ""
        return fileStr.substringAfterAndBefore("@$annotationName", propertyName)
            .substringAfterAndBefore("(", ")")
    }

    /**
     * 获取注解内参数的代码内容
     */
    private fun getAnnotationArgumentIdCode(
        annotationCode: String?,
        argumentName: String
    ): String? {
        if (annotationCode.isNullOrEmpty()) return ""
        return annotationCode.split(",").find {
            it.contains(argumentName)
        }?.substringAfter("=")?.trim()
    }

    private fun delR(argumentsCode: String?): String {
        if (argumentsCode.isNullOrEmpty()) return ""
        return argumentsCode.substringAfterLast(".")
    }

    private fun String.substringAfterAndBefore(after: String, before: String): String {
        return substringAfter(after).substringBefore(before)
    }

    override fun visitEnd() {
        if (outFileName.isNullOrEmpty()) return
        if (outFileSpec == null) {
            outFileSpec = FileSpec.builder(packageName, outFileName!!)
        }
        classTypeBuilder?.addFunction(findViewFunBuilder!!.build())
        outFileSpec?.addType(classTypeBuilder!!.build())
        super.visitEnd()
    }
}