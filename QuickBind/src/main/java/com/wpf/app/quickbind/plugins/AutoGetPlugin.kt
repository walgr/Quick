package com.wpf.app.quickbind.plugins

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.wpf.app.quickbind.annotations.AutoGet
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
class AutoGetPlugin : BindBasePlugin {

    override fun dealField(obj: Any, viewModel: ViewModel?, field: Field) {
        var objTemp = obj
        val autoGet: AutoGet = field.getAnnotation(AutoGet::class.java) ?: return
        var bundle: Bundle? = null
        if (objTemp is Activity) {
            bundle = objTemp.intent.extras
        } else if (objTemp is Fragment) {
            bundle = objTemp.arguments
        }
        if (bundle == null) return
        if (viewModel != null) {
            objTemp = viewModel
        }
        field.isAccessible = true
        var key = field.name
        if (autoGet.value.isNotEmpty()) {
            key = autoGet.value
        }
        if (arrayContains(field.type.interfaces, Parcelable::class.java)) {
            try {
                //Parcelable
                val value = bundle.getParcelable<Parcelable>(key)
                if (value != null) {
                    field[objTemp] = value
                    return
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        try {
            //Serializable
            val value = bundle.getSerializable(key)
            if (value != null) {
                if (value.javaClass.isArray) {
                    if ((value as Array<*>).size != 0 && value[0] is Parcelable) {
                        //TODO 暂不支持Parcelable[]
                    } else {
                        field[objTemp] = value
                    }
                } else {
                    field[objTemp] = value
                    return
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            if (getFieldType(field) == 2) {
                //ArrayList
                val value = bundle.getParcelableArrayList<Parcelable>(key)
                if (value != null) {
                    field[objTemp] = value
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return
    }


    private fun getFieldType(field: Field): Int {
        val type = field.genericType
        if (type is Class<*> && type.isArray) {
            return 1
        } else if (type is ParameterizedType) {
            return 2
        }
        return 0
    }

    private fun arrayContains(classes: Array<Class<*>>?, cls: Class<*>): Boolean {
        if (classes == null) return false
        for (c in classes) {
            if (c == cls) return true
        }
        return false
    }
}