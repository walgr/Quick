package com.wpf.app.quickbind.utils

import android.app.Dialog
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Field
import java.util.*

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
object ReflectHelper {

    fun getFieldWithParent(@NonNull obj: Any): List<Field> {
        val result = ArrayList<Field>()
        var curCls: Class<*>? = obj.javaClass
        while (curCls != null) {
            result.addAll(curCls.declaredFields)
            curCls = curCls.superclass
            if (curCls == AppCompatActivity::class.java
                || curCls == Fragment::class.java
                || curCls == Dialog::class.java
                || curCls == RecyclerView.ViewHolder::class.java
                || curCls == View::class.java
                || curCls == ViewModel::class.java) {
                break
            }
        }
        return result
    }

    /**
     * 向上扫描是否可以停止
     */
    fun canBreakScan(curCls: Class<*>?): Boolean {
        if (curCls == null) return true
        val clsName = curCls.name
        return clsName.startsWith("android.")
                || clsName.startsWith("androidx.")
                || clsName.startsWith("java.")
    }
}