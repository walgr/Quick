package com.wpf.app.plugin.factory.mixin

data class ClassData(
    val name: String,

    val interfaceList: List<ClassInterface>? = null
)

data class ClassField(val name: String, val typeName: String)
data class ClassInterface(val name: String)