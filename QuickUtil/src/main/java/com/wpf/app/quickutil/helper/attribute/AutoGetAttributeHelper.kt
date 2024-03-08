package com.wpf.app.quickutil.helper.attribute

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.StyleableRes

object AutoGetAttributeHelper {
    inline fun <reified T : Any> init(
        context: Context,
        attrs: AttributeSet? = null,
        @StyleableRes styleableId: IntArray
    ): T {
        val data = T::class.java.getDeclaredConstructor().newInstance()
        if (attrs != null) {
            AutoGetAttribute(context, attrs, styleableId, data)
        }
        return data
    }
}