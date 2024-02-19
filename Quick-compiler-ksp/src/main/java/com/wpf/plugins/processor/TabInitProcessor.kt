package com.wpf.plugins.processor

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.UNIT
import com.wpf.app.quick.annotations.tab.TabInit
import com.wpf.app.quick.annotations.tab.view.ViewType
import java.io.File

class TabInitProcessor(environment: SymbolProcessorEnvironment) : IdProcessor(environment) {
    private var funBuilderMap = mutableMapOf<String, FunSpec.Builder>()
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
        val layoutId = tabInitAnn.arguments[0].value as? Int ?: 0
        val funName = tabInitAnn.arguments[1].value as? String ?: ""
        val idTypeStrList =
            (tabInitAnn.arguments[if (funName.isEmpty()) 1 else 2].value as ArrayList<KSAnnotation>).map { it.arguments.joinToString() }
        val idTypePairList = idTypeStrList.map {
            Pair(
                it.substringAfterAndBeforeNoInclude("id:"),
                ViewType.valueOf(
                    it.substringAfterAndBeforeNoInclude(
                        "viewClass:${ViewType::class.java.name}.",
                        ","
                    )
                )
            )
        }.toMutableList()
        if (idTypePairList.isEmpty()) {
            var layoutFileStr = File(
                mainPath + "/res/layout/".replace(
                    "/",
                    File.separator
                ) + layoutIdName + ".xml"
            ).readText().replace("\r", "").replace("\n", "")
            val idFind = "android:id=\"@+id/"
            if (layoutFileStr.contains(idFind)) {
                var findPos = -1
                do {
                    if (findPos != -1) {
                        layoutFileStr = layoutFileStr.substring(findPos + idFind.length)
                    }
                    findPos = layoutFileStr.indexOf(idFind, findPos + 1)
                    val typeIdStr = layoutFileStr.substring(
                        layoutFileStr.indexOfWithBefore("<", idFind),
                        layoutFileStr.indexOfWithAfter("\"", idFind)
                    )
                    val typeStr = typeIdStr.substringAfterAndBeforeNoInclude("<", idFind).trim()
                    val idStr = typeIdStr.substringAfterAndBeforeNoInclude(idFind, "\"").trim()
                    idTypePairList.add(
                        Pair(
                            "R.id.${idStr}",
                            if (typeStr.contains(".")) ViewType.valueOf(
                                typeStr.split(".").last()
                            ) else ViewType.valueOf(typeStr)
                        )
                    )
                } while (findPos != -1)
            }
        }
        val code = "val tabManager =\n" +
                "        init(layoutId, parent, size, defaultPos, repeatClick) { curPos, isSelect, view ->\n" +
                "            init.invoke(curPos, isSelect, " +
                idTypePairList.joinToString {
                    "view.findViewById(${
                        it.first.replace(
                            "R",
                            "%T"
                        )
                    })"
                } + ")\n" +
                "        }\n" +
                "    return tabManager"
        val args = idTypePairList.map {
            ClassName(
                packageName,
                "R"
            )
        }.toTypedArray()
        val idNameList = if (code.contains("%T")) idTypePairList.map { it.first.replace("R.id.", "") } else getAnnotationArgumentIdCode(fileStr, TabInit::class)
        funBuilderMap[funName.ifEmpty { layoutIdName }] =
            FunSpec.builder(funName.ifEmpty { layoutIdName })
                .receiver(ClassName("com.wpf.app.quickwidget.tab.", "TabManager"))
                .returns(ClassName("com.wpf.app.quickwidget.tab.", "TabManager"))
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
                .addCode(CodeBlock.of(code, *args))
    }

    override fun visitEnd() {
        if (outFileName.isNullOrEmpty()) return
        packageName = "com.wpf.quick.helper"
        if (outFileSpec == null) {
            outFileSpec = FileSpec.builder(packageName, outFileName!!)
        }
        funBuilderMap.forEach { (_, funBuilder) ->
            outFileSpec?.addFunction(funBuilder.build())
        }
        super.visitEnd()
    }
}