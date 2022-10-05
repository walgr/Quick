package com.wpf.app.quick.helper

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wpf.app.quickutil.LogUtil

/**
 * Created by 王朋飞 on 2022/9/16.
 *
 */
object GsonHelper {

    fun getExposeGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    fun getNoParentGson(): Gson {
        return GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
            override fun shouldSkipField(f: FieldAttributes?): Boolean {
                f?.let {
                    com.wpf.app.quickutil.LogUtil.e("过滤字段${it.name}  ${it.declaringClass}")
                    return isParentHaveField(it.declaringClass, it.name)
                }
                return false
            }

            override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                return false
            }

            private fun isParentHaveField(fieldCls: Class<*>, fieldName: String): Boolean {
                var have = false
                var parentCls = fieldCls.superclass
                while (parentCls != null) {
                    try {
                        parentCls.getDeclaredField(fieldName)
                        have = true
                    } catch (ignore: Exception) { }
                    parentCls = parentCls.superclass
                }
                return have
            }

        }).create()
    }
}