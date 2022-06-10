package com.wpf.app.quick.base.helper

import android.content.Context
import android.util.AttributeSet

/**
 * Created by 王朋飞 on 2022/6/10.
 * 给View附加Sp功能
 *
 */
interface ViewSpHelper {
    var attributeHelper: SpViewAttributeHelper?

    fun initAttributeHelper(context: Context, attributes: AttributeSet) {
        attributeHelper = SpViewAttributeHelper(context, attributes)
    }

    fun getValue(context: Context): String {
        if (attributeHelper?.bindKey?.isNotBlank() == true) {
            return context.getSharedPreferences(this::class.java.name, Context.MODE_PRIVATE)
                .getString(attributeHelper?.bindKey, "") ?: ""
        }
        return ""
    }

    fun updateValue(context: Context, newValue: String) {
        if (getValue(context) == newValue) return
        if (attributeHelper?.bindKey?.isNotBlank() == true) {
            context.getSharedPreferences(this::class.java.name, Context.MODE_PRIVATE).edit()
                .putString(attributeHelper?.bindKey, newValue).apply()
        }
    }
}