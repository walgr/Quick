package com.wpf.app.quick.base.helper

import android.content.Context
import android.util.AttributeSet
import com.wpf.app.quick.base.R

/**
 * Created by 王朋飞 on 2022/6/10.
 *
 */
class SpViewAttributeHelper(
    context: Context,
    attributeSet: AttributeSet
): AutoGetAttributeHelper(context, attributeSet, R.styleable.SpView) {

    val bindKey: String? = null
}