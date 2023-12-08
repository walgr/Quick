package com.wpf.app.quickutil.helper.attribute

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.StyleableRes

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

    fun recycle() {
        if (typeArray.length() != 0) {
            typeArray.recycle()
        }
    }
}