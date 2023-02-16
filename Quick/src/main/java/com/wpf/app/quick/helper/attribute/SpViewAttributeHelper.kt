package com.wpf.app.quick.helper.attribute

import android.content.Context
import android.util.AttributeSet
import com.wpf.app.quick.R
import com.wpf.app.quick.helper.attribute.base.AutoGetAttributeHelper

/**
 * Created by 王朋飞 on 2022/6/10.
 *
 */
class SpViewAttributeHelper(
    context: Context,
    attributeSet: AttributeSet
): AutoGetAttributeHelper(context, attributeSet, R.styleable.LoadSp2Text) {

    val fileName: String? = null
    val bindKey: String? = null
    val defaultString: String? = null
    val bindData2Sp: Boolean? = null
}