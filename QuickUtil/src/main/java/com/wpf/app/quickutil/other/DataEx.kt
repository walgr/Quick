package com.wpf.app.quickutil.other

fun <K: Any, V: Any> MutableMap<K, V>.emptyPut(key: K, value: V) {
    if (get(key) == null) {
        put(key, value)
    }
}