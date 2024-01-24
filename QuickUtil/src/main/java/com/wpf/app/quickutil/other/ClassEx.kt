package com.wpf.app.quickutil.other

import java.lang.reflect.Field

fun Class<*>.getFieldWithSuper(): List<Field> {
    val fieldList = mutableListOf<Field>()
    fieldList.addAll(declaredFields)
    var parentCls = superclass
    while (parentCls != null && parentCls != Any::class.java) {
        fieldList.addAll(parentCls.declaredFields)
        parentCls = parentCls.superclass
    }
    return fieldList
}

fun Class<*>.getClassWithSuper(): List<Class<*>> {
    val list = mutableListOf<Class<*>>()
    list.add(this)
    var parentCls = superclass
    while (parentCls != null && parentCls != Any::class.java) {
        list.add(parentCls)
        parentCls = parentCls.superclass
    }
    return list
}