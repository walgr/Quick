package com.wpf.plugins.processor

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.UNIT
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.wpf.app.quick.annotations.IdView
import com.wpf.app.quick.annotations.TabInit
import com.wpf.app.quick.annotations.view.ViewType

class TabInitProcessor(environment: SymbolProcessorEnvironment) : IdProcessor(environment) {
    private var funBuilder: FunSpec.Builder? = null
    override fun visitPropertyDeclaration(
        property: KSPropertyDeclaration,
        data: Unit,
        packageName: String,
        className: String,
        propertyName: String?
    ) {
        super.visitPropertyDeclaration(property, data, packageName, className, propertyName)


        val tabInitAnn = property.annotations.find {
            it.shortName.getShortName().contains(TabInit::class.simpleName!!)
        }
        if (tabInitAnn?.arguments == null) return
        outFileName = "TabInitHelper"
        val layoutIdName = getAnnotationArgumentIdCode(fileStr, TabInit::class, "layout").first()
        val layoutId = tabInitAnn.arguments[0].value as Int
        val funName = tabInitAnn.arguments[1].value as String
        val idTypeStrList =
            (tabInitAnn.arguments[2].value as ArrayList<KSAnnotation>).map { it.arguments.joinToString() }
        val idTypePairList = idTypeStrList.map {
            Pair(
                it.substringAfterAndBeforeNoInclude("id:"),
                ViewType.valueOf(it.substringAfterAndBeforeNoInclude("viewClass:${ViewType::class.java.name}.", ","))
            )
        }
        val idNameList = getAnnotationArgumentIdCode(fileStr, TabInit::class)
        if (funBuilder == null) {
            funBuilder = FunSpec.builder(funName.ifEmpty { layoutIdName })
                .receiver(ClassName("com.wpf.app.quick.helper.tab", "TabManager"))
                .returns(ClassName("com.wpf.app.quick.helper.tab", "TabManager"))
                .addParameter(
                    "parent",
                    ClassName("android.view", "ViewGroup").copy(nullable = true)
                )
                .addParameter("size", Int::class)
                .addParameter(
                    ParameterSpec.builder("defaultPos", Int::class).defaultValue("0").build()
                )
                .addParameter(
                    ParameterSpec.builder("repeatClick", Boolean::class).defaultValue("false")
                        .build()
                )
                .addParameter(
                    ParameterSpec.builder("layoutId", Int::class).addAnnotation(
                        AnnotationSpec.builder(ClassName("androidx.annotation", "LayoutRes"))
                            .build()
                    ).defaultValue(layoutId.toString()).build()
                )
                .addParameter(ParameterSpec.builder("init",
                    LambdaTypeName.get(null, mutableListOf(
                        ParameterSpec.builder("curPos", Int::class).build(),
                        ParameterSpec.builder("isSelect", Boolean::class).build(),
                    ).plus(
                        idNameList.mapIndexed { index, it ->
                            ParameterSpec.builder(
                                it,
                                ClassName(
                                    idTypePairList[index].second.packageName,
                                    idTypePairList[index].second.className
                                )
                            ).build()
                        }
                    ), UNIT)).build())
                .addCode(
                    "val tabManager =\n" +
                            "        init(layoutId, parent, size, defaultPos, repeatClick) { curPos, isSelect, view ->\n" +
                            "            init.invoke(curPos, isSelect, " +
                            idNameList.joinToString { "findChild(view, R.id.${it})" } + ")\n" +
                            "        }\n" +
                            "    return tabManager"
                )
        }
    }

    override fun visitEnd() {
        if (outFileName.isNullOrEmpty()) return
        if (outFileSpec == null) {
            outFileSpec = FileSpec.builder(packageName, outFileName!!)
        }
        outFileSpec?.addFunction(funBuilder?.build()!!)
        super.visitEnd()
    }
}