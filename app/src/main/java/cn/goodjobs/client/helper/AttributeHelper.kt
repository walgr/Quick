package cn.goodjobs.client.helper

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.StyleableRes
import androidx.core.content.res.getTextOrThrow
import cn.goodjobs.client.R

/**
 * Created by 王朋飞 on 2022/4/29.
 * 参数配置帮助类
 */

open class AttributeHelper constructor(
    context: Context,
    attributeSet: AttributeSet,
    @StyleableRes styleableId: IntArray
) {

    val typeArray by lazy {
        context.obtainStyledAttributes(attributeSet, styleableId, 0, 0)
    }

    fun <T : Any> getAttribute(@StyleableRes index: Int, defValue: T): T {
        when (defValue) {
            is Int -> {
                return typeArray.getInt(index, defValue) as T
            }
            is Float -> {
                return typeArray.getFloat(index, defValue) as T
            }
            is String -> {
                return (typeArray.getString(index) ?: defValue) as T
            }
            is Boolean -> {
                return typeArray.getBoolean(index, defValue) as T
            }
            is ColorInt -> {
                return typeArray.getColor(index, defValue as Int) as T
            }
            is Float -> {
                return typeArray.getDimension(index, defValue) as T
            }
        }
        return defValue
    }

    public fun recycle() {
        typeArray.recycle()
    }
}