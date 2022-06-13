package com.wpf.app.quick.base.helper.attribute.base

import android.content.Context
import android.content.res.XmlResourceParser
import android.graphics.Color
import android.util.AttributeSet
import androidx.annotation.StyleableRes
import java.lang.reflect.Field

/**
 * Created by 王朋飞 on 2022/4/29.
 * 参数配置帮助类
 * 按照xml里配置顺序读取数据
 */

abstract class AutoGetAttributeHelper constructor(
    context: Context,
    attributeSet: AttributeSet,
    @StyleableRes styleableId: IntArray
): AttributeHelper(context, attributeSet, styleableId) {

    private val fieldMap: MutableMap<String, Field> = mutableMapOf()
    init {
        getAllClassField()
        getAttributeType(attributeSet, styleableId)
    }

    private fun getAttributeType(attributeSet: AttributeSet, @StyleableRes styleableId: IntArray) {
        for (i in 0 until attributeSet.attributeCount) {
            val attributeResNameSpace = (attributeSet as? XmlResourceParser)?.getAttributeNamespace(i)
            if ("http://schemas.android.com/apk/res-auto" != attributeResNameSpace) continue
            val attributeName = attributeSet.getAttributeName(i)
            val attributeId = attributeSet.getAttributeNameResource(i)
            if (styleableId.contains(attributeId) && fieldMap.contains(attributeName)) {
                val field = fieldMap[attributeName]
                when(field?.type) {
                    java.lang.Integer::class.java, Int::class.java -> {
                        //只能获取dp、sp、资源id
                        val attributeValue = attributeSet.getAttributeValue(i)
                        if (attributeValue.contains("dip") || attributeValue.contains("sp")) {
                            //获取dp或sp
                            field.set(this, typeArray.getDimensionPixelSize(styleableId.indexOf(attributeId), 0))
                        } else if (attributeValue.startsWith("@")) {
                           //获取资源id
                            field.set(this, attributeSet.getAttributeResourceValue(i, 0))
                        } else {
//                            field.set(this, attributeSet.getAttributeIntValue(i, 0))
                        }
                    }
                    java.lang.Float::class.java, Float::class.java -> {
                        field.set(this, attributeSet.getAttributeFloatValue(i, 0f))
                    }
                    java.lang.String::class.java, String::class.java -> {
                        field.set(this, attributeSet.getAttributeValue(i))
                    }
                    java.lang.Boolean::class.java, Boolean::class.java -> {
                        field.set(this, attributeSet.getAttributeBooleanValue(i, false))
                    }
                    Color::class.java -> {
                        val attributeValue = attributeSet.getAttributeValue(i)
                        if (attributeValue.startsWith("#")) {
                            //获取资源id
                            field.set(this, Color.parseColor(attributeValue))
                        }
                    }
                }

            }
        }
    }

    private fun getAllClassField() {
        val fieldList = this.javaClass.declaredFields
        fieldList.forEach {
            it.isAccessible = true
            fieldMap[it.name] = it
        }
    }
}