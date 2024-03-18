package com.wpf.app.quickutil.other

import android.app.Dialog
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.util.ArrayList

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

    /**
     * 获取当前类和父类所有属性
     */
    fun getFieldAndParent(obj: Any): List<Field> {
        val result = ArrayList<Field>()
        var curCls: Class<*>? = obj.javaClass
        while (curCls != null) {
            result.addAll(curCls.declaredFields)
            curCls = curCls.superclass
            if (curCls == AppCompatActivity::class.java
                || curCls == Fragment::class.java
                || curCls == Dialog::class.java
                || curCls == RecyclerView.ViewHolder::class.java
                || curCls == View::class.java
                || curCls == ViewModel::class.java) {
                break
            }
        }
        return result
    }

    /**
     * 向上扫描是否可以停止
     */
    fun canBreakScan(curCls: Class<*>?): Boolean {
        if (curCls == null) return true
        val clsName = curCls.name
        return clsName.startsWith("android.")
                || clsName.startsWith("androidx.")
                || clsName.startsWith("java.")
    }
}