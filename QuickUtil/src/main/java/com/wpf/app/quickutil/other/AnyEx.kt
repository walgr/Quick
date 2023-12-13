package com.wpf.app.quickutil.other

inline fun <reified T> Any?.asTo(): T? {
    if (this == null) return null
    if (this !is T) return null
    return this
}