package com.wpf.app.quickutil.base

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * Created by 王朋飞 on 2022/8/4.
 *
 */

fun Int.toDrawable(context: Context): Drawable? {
    return ContextCompat.getDrawable(context, this)
}

fun Int.toColor(context: Context): Int {
    return ContextCompat.getColor(context, this)
}

/**
 * 设置字重
 * 只能100-900
 * 400 600 最好通过设置style实现
 */
fun TextView.fontFamily(fontWidget: Int) {
    if (fontWidget < 100 || fontWidget > 900 || fontWidget%100 != 0) {
        throw RuntimeException("字重必须100-900")
    }
    val typeface = when (fontWidget) {
        100 -> {
            Typeface.create("sans-serif-thin", Typeface.NORMAL)
        }
        200-> {
            Typeface.create("sans-serif-condensed-light", Typeface.NORMAL)
        }
        300-> {
            Typeface.create("sans-serif-light", Typeface.NORMAL)
        }
        400-> {
            Typeface.create("sans-serif", Typeface.NORMAL)
        }
        500-> {
            Typeface.create("sans-serif-medium", Typeface.NORMAL)
        }
        600-> {
            Typeface.create("sans-serif", Typeface.BOLD)
        }
        700-> {
            //TODO 未找到对应的
            Typeface.create("sans-serif", Typeface.BOLD)
        }
        800-> {
            //TODO 未找到对应的
            Typeface.create("sans-serif-black", Typeface.NORMAL)
        }
        900-> {
            Typeface.create("sans-serif-black", Typeface.NORMAL)
        }
        else -> {
            Typeface.create("sans-serif", Typeface.NORMAL)
        }
    }
    paint?.typeface = typeface
}