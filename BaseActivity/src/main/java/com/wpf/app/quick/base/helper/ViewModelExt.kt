package com.wpf.app.quick.base.helper

import java.lang.reflect.ParameterizedType

/**
 * ViewModel 扩展功能类
 */

/**
 * 获取当前类绑定的泛型ViewModel-clazz
 */
inline fun <reified VM> getVm0Clazz(obj: Any): VM? {
    val superCls = obj.javaClass.genericSuperclass
    if (superCls is ParameterizedType) {
        if (superCls.actualTypeArguments.isNotEmpty()) {
            val type = superCls.actualTypeArguments[0]
            if (type is VM) {
                return type
            }
        }
    }
    return null
}

/**
 * 获取当前类绑定的泛型ViewBinding-clazz
 */
@Suppress("UNCHECKED_CAST")
fun <VB> getVm1Clazz(obj: Any): VB {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as VB
}
