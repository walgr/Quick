package com.wpf.app.quickutil.base

fun <T> Any?.asTo(): T? {
    return this as? T?
}