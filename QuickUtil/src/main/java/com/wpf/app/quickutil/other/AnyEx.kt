package com.wpf.app.quickutil.other

import com.wpf.app.quickutil.log.LogUtil
import java.math.BigDecimal

inline fun <reified T> Any?.asTo(): T? {
    if (this == null) return null
    if (this !is T) return null
    return this
}

inline fun <reified T> Any.forceTo(): T {
    return this as T
}

fun <T: Any> T?.nullDefault(default: T) : T {
    return this ?: default
}

fun Any.printLog(before: String = "", after: String = "", tag: String = LogUtil.TAG) {
    LogUtil.e(tag, before + (if (this is Number) BigDecimal(this.toString()) else this) + after)
}