package com.wpf.app.quickutil

import java.lang.reflect.ParameterizedType

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
object GenericEx {

    fun <VM : Class<out Any>> get0Clazz(obj: Any): VM? {
        val superCls = obj.javaClass.genericSuperclass
        if (superCls is ParameterizedType) {
            val actualType = superCls.actualTypeArguments
            if (actualType.isNotEmpty()) {
                val type = actualType[0]
                return if (type is ParameterizedType) {
                    type.rawType as? VM
                } else {
                    type as? VM
                }
            }
        }
        return null
    }

    fun <VB> get1Clazz(obj: Any): VB {
        return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as VB
    }
}