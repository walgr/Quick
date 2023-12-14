package com.wpf.app.quickutil.other

import androidx.lifecycle.ViewModel
import java.lang.reflect.ParameterizedType

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
object ViewModelEx {

    inline fun <reified VM : Class<out ViewModel>> get0Clazz(obj: Any): VM? {
        val superCls = obj.javaClass.genericSuperclass
        if (superCls is ParameterizedType) {
            val actualType = superCls.actualTypeArguments
            if (actualType.isNotEmpty()) {
                val type = actualType[0]
                if (type is ParameterizedType) {
                    return type.rawType as VM
                } else if (type is VM) {
                    return type
                }
            }
        }
        val actualType = obj.javaClass.typeParameters
        if (actualType.isNotEmpty()) {
            val type = actualType[0]
            if (type is ParameterizedType) {
                return type.rawType as VM
            } else if (type is VM) {
                return type
            }
        }
        return null
    }
}