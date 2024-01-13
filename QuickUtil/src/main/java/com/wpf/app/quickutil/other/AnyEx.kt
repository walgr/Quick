package com.wpf.app.quickutil.other

import com.wpf.app.quickutil.LogUtil

inline fun <reified T> Any?.asTo(): T? {
    if (this == null) return null
    if (this !is T) return null
    return this
}

inline fun <reified T> Any.to(): T {
    return this as T
}

fun Any.printLog(otherMsg: String = "", tag: String = LogUtil.tag) {
    LogUtil.e(tag, otherMsg + if (this is Float) this.toBigDecimal() else this)
}