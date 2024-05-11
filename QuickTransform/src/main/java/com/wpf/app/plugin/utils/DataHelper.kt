package com.wpf.app.plugin.utils

object DataHelper {
    private val dataMap = mutableMapOf<String, Any>()

    fun saveData(key: String, value: Any) {
        dataMap[key] = value
    }

    fun getData(key: String): Any? = dataMap[key]

    fun deleteData(key: String) {
        dataMap.remove(key)
    }

    operator fun set(key: String, value: Any) {
        saveData(key, value)
    }

    operator fun get(key: String): Any? = getData(key)
    fun <T : Any> getT(key: String): T? = getData(key) as? T
    fun getNullDefault(key: String, default: Any): Any = getData(key) ?: default
    fun <T : Any> getTNullDefault(key: String, default: T): T = (getData(key) as? T) ?: default
    fun <T : Any> getNullPut(key: String, default: T): T {
        val value = getData(key) as? T
        if (value == null) {
            saveData(key, default)
        }
        return value ?: default
    }
}