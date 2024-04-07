package com.wpf.app.quickutil.data

import com.wpf.app.quickutil.init.SpManager

fun Int.setToSp(
    key: String,
    fileName: String = "QuickViewSpBindFile",
) {
    SpManager.getSharedPreference(fileName)?.edit()?.apply {
        putInt(key, this@setToSp)
        apply()
    }
}

fun Long.setToSp(
    key: String,
    fileName: String = "QuickViewSpBindFile",
) {
    SpManager.getSharedPreference(fileName)?.edit()?.apply {
        putLong(key, this@setToSp)
        apply()
    }
}

fun Boolean.setToSp(
    key: String,
    fileName: String = "QuickViewSpBindFile",
) {
    SpManager.getSharedPreference(fileName)?.edit()?.apply {
        putBoolean(key, this@setToSp)
        apply()
    }
}

fun Float.setToSp(
    key: String,
    fileName: String = "QuickViewSpBindFile",
) {
    SpManager.getSharedPreference(fileName)?.edit()?.apply {
        putFloat(key, this@setToSp)
        apply()
    }
}


fun String.setToSp(
    key: String,
    fileName: String = "QuickViewSpBindFile",
) {
    SpManager.getSharedPreference(fileName)?.edit()?.apply {
        putString(key, this@setToSp)
        apply()
    }
}

inline fun <reified T : Any> String.putSpValue(
    value: T?,
    fileName: String = "QuickViewSpBindFile",
) {
    SpManager.getSharedPreference(fileName)?.edit()?.apply {
        when (T::class.java) {
            String::class.java -> {
                putString(this@putSpValue, value as? String)
            }

            Int::class.java -> {
                putInt(this@putSpValue, value as? Int ?: 0)
            }

            Float::class.java -> {
                putFloat(this@putSpValue, value as? Float ?: 0F)
            }

            Boolean::class.java -> {
                putBoolean(this@putSpValue, value as? Boolean ?: false)
            }

            Long::class.java -> {
                putLong(this@putSpValue, value as? Long ?: 0L)
            }
        }
        apply()
    }
}

inline fun <reified T : Any> String.getSpValue(
    defaultValue: T? = null,
    fileName: String = "QuickViewSpBindFile",
): T? {
    val sp = SpManager.getSharedPreference(fileName) ?: return null
    return when (T::class.java) {
        String::class.java -> {
            sp.getString(this@getSpValue, defaultValue as? String) as? T
        }

        Int::class.java -> {
            sp.getInt(this@getSpValue, defaultValue as? Int ?: 0) as? T
        }

        Float::class.java -> {
            sp.getFloat(this@getSpValue, defaultValue as? Float ?: 0f) as? T
        }

        Boolean::class.java -> {
            sp.getBoolean(this@getSpValue, defaultValue as? Boolean ?: false) as? T
        }

        Long::class.java -> {
            sp.getLong(this@getSpValue, defaultValue as? Long ?: 0L) as? T
        }

        else -> {
            defaultValue as T
        }
    }
}