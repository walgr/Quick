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
 * 按照xml里配置顺序读取数据
 */

abstract class OrderAttributeHelper constructor(
    context: Context,
    attributeSet: AttributeSet,
    @StyleableRes styleableId: IntArray
): AttributeHelper(context, attributeSet, styleableId) {

    var orderStyleableId: IntArray = IntArray(styleableId.size)

    init {
        getAttributeType(attributeSet, styleableId)
    }

    private fun getAttributeType(attributeSet: AttributeSet, @StyleableRes styleableId: IntArray) {
        var orderPos = 0
        for (i in 0 until attributeSet.attributeCount) {
            val attributeResId = attributeSet.getAttributeNameResource(i)
            if (styleableId.contains(attributeResId)) {
                orderStyleableId[orderPos++] = styleableId.indexOf(attributeResId)
            }
        }
    }

    abstract fun attributeOrder() : IntArray
}