package com.wpf.app.quickutil.other

import java.lang.reflect.ParameterizedType

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
object GenericEx {

    inline fun <reified Clazz : Class<out Any>> get0Clazz(obj: Any): Clazz? {
        val superCls = obj.javaClass.genericSuperclass
        if (superCls is ParameterizedType) {
            val actualType = superCls.actualTypeArguments
            if (actualType.isNotEmpty()) {
                val type = actualType[0]
                if (type is ParameterizedType) {
                    return type.rawType as Clazz
                } else if (type is Clazz) {
                    return type
                }
            }
        }
        val actualType = obj.javaClass.typeParameters
        if (actualType.isNotEmpty()) {
            val type = actualType[0]
            if (type is ParameterizedType) {
                return type.rawType as Clazz
            } else if (type is Clazz) {
                return type
            }
        }
        return null
    }
}