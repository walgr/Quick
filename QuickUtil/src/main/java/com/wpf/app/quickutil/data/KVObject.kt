package com.wpf.app.quickutil.data

object KVObject {
    private val data = mutableMapOf<Any, Any>()

    fun put(key: Any, data: Any) {
        this.data[key] = data
    }

    fun <T: Any> get(key: Any): T? = this.data[key] as? T

    fun <T: Any> get(key: Any, default: T): T = get(key) ?: default
}