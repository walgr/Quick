package com.wpf.app.quick.annotations.bind

interface Databinder {

    fun getFieldValue(fieldName: String): Any? {
        try {
            val findF = javaClass.getDeclaredField(fieldName)
            findF.isAccessible = true
            return findF[this]
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return null
    }
}