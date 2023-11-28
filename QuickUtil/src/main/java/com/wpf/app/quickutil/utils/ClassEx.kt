package com.wpf.app.quickutil.utils

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