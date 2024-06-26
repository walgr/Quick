package com.wpf.app.quickutil.helper.attribute

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.StyleableRes
import androidx.core.content.ContextCompat
import java.lang.reflect.Field

/**
 * Created by 王朋飞 on 2022/4/29.
 * 参数配置帮助类
 * 按照xml里配置顺序读取数据
 * 字段属性不可设置默认值
 */

open class AutoGetAttribute(
    context: Context,
    attributeSet: AttributeSet?,
    @StyleableRes styleableId: IntArray,
    //数据保存的地方
    private var saveData: Any? = null,
) : AttributeHelper(context, attributeSet, styleableId) {

    private val fieldMap = mutableMapOf<String, Field>()

    init {
        getAllClassField()
        getAttributeType(context, attributeSet, styleableId)
        recycle()
    }

    private fun getAttributeType(
        context: Context,
        attributeSet: AttributeSet?,
        @StyleableRes styleableId: IntArray
    ) {
        if (attributeSet == null) return
        for (i in 0 until attributeSet.attributeCount) {
//            val attributeResNameSpace = (attributeSet as? XmlResourceParser)?.getAttributeNamespace(i)
//            if ("http://schemas.android.com/apk/res-auto" != attributeResNameSpace) continue
            val attributeName = attributeSet.getAttributeName(i)
            val attributeId = attributeSet.getAttributeNameResource(i)
            if (styleableId.contains(attributeId) && fieldMap.contains(attributeName)) {
                val field = fieldMap[attributeName]
                field?.isAccessible = true
                when (field?.type) {
                    java.lang.Integer::class.java, Int::class.java -> {
                        //只能获取dp、sp、资源id
                        val attributeValue = attributeSet.getAttributeValue(i)
                        if (attributeValue.contains("dip")
                            || attributeValue.contains("dp")
                            || attributeValue.contains("sp")
                        ) {
                            //获取dp或sp
                            field.set(
                                saveData ?: this,
                                typeArray.getDimensionPixelSize(styleableId.indexOf(attributeId), 0)
                            )
                        } else if (attributeValue.startsWith("@")) {
                            //获取资源id
                            val res = attributeSet.getAttributeResourceValue(i, 0)
                            //获取layout
                            field.set(saveData ?: this, res)
//                            if (!testView.isInEditMode) {
//                                try {
//                                    //获取color
//                                    val colorInt = ContextCompat.getColor(context, res)
//                                    if (colorInt != 0) {
//                                        field.set(saveData ?: this, colorInt)
//                                    }
//                                } catch (ignore: Exception) {
//                                    try {
//                                        //获取drawable
//                                        val drawable = ContextCompat.getDrawable(context, res)
//                                        if (drawable != null) {
//                                            field.set(saveData ?: this, drawable)
//                                        }
//                                    } catch (ignore: Exception) {
//
//                                    }
//                                }
//                            }
                        } else if (attributeValue.startsWith("#")) {
                            //获取资源id
                            field.set(saveData ?: this, Color.parseColor(attributeValue))
                        } else {
                            field.set(saveData ?: this, attributeSet.getAttributeIntValue(i, 0))
                        }
                    }

                    java.lang.Float::class.java, Float::class.java -> {
                        field.set(saveData ?: this, attributeSet.getAttributeFloatValue(i, 0f))
                    }

                    java.lang.String::class.java, String::class.java -> {
                        field.set(saveData ?: this, attributeSet.getAttributeValue(i))
                    }

                    java.lang.Boolean::class.java, Boolean::class.java -> {
                        field.set(saveData ?: this, attributeSet.getAttributeBooleanValue(i, false))
                    }

                    Color::class.java -> {
                        val attributeValue = attributeSet.getAttributeValue(i)
                        if (attributeValue.startsWith("#")) {
                            //获取资源id
                            field.set(saveData ?: this, Color.parseColor(attributeValue))
                        } else if (attributeValue.startsWith("@")) {
                            val res = attributeSet.getAttributeResourceValue(i, 0)
                            val colorInt = ContextCompat.getColor(context, res)
                            if (colorInt != 0) {
                                field.set(saveData ?: this, colorInt)
                            }
                        }
                    }

                    Drawable::class.java -> {
                        val attributeValue = attributeSet.getAttributeValue(i)
                        if (attributeValue.startsWith("@")) {
                            val res = attributeSet.getAttributeResourceValue(i, 0)
                            ContextCompat.getDrawable(context, res)?.let {
                                field.set(saveData ?: this, it)
                            }
                        }
                    }
                }
            }
        }
        fieldMap.clear()
    }

    private fun getAllClassField() {
        if (saveData == null) {
            saveData = this
        }
        saveData!!.javaClass.declaredFields.forEach {
            fieldMap[it.name] = it
        }
    }

    @ColorInt
    fun getColorInt(context: Context, color: Int): Int {
        return if (color > 0) {
            ContextCompat.getColor(context, color)
        } else {
            color
        }
    }
}