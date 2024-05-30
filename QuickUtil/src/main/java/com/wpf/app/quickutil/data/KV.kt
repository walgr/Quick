package com.wpf.app.quickutil.data

@Suppress("UNCHECKED_CAST")
object KV {
    private val data = mutableMapOf<Any, Any?>()

    fun put(key: Any, data: Any?) {
        this.data[key] = data
    }

    fun putIfNull(key: Any, data: () -> Any?) {
        if (get<Any>(key) == null) {
            put(key, data.invoke())
        }
    }

    fun <T: Any> get(key: Any): T? = this.data[key] as? T

    fun <T: Any> get(key: Any, default: T): T = get(key) ?: default

    fun <T: Any> getIfNullPut(key: Any, default: T): T {
        val value = get<T>(key)
        if (value == null) {
            put(key, default)
        }
        return value ?: default
    }

    fun remove(key: Any) {
        data.remove(key)
    }
}