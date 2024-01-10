package com.wpf.app.quickutil.other

import com.wpf.app.quickutil.LogUtil

inline fun <reified T> Any?.asTo(): T? {
    if (this == null) return null
    if (this !is T) return null
    return this
}

fun Any.printLog() {
    LogUtil.e(this.toString())
}