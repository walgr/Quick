package com.wpf.app.quickbind.plugins

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quick.runtime.Databinder
import com.wpf.app.quickbind.QuickBind
import java.lang.reflect.Field

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface BasePlugin {

    @NonNull
    fun getRealObj(@NonNull obj: Any, @Nullable viewModel: ViewModel?): Any {
        return viewModel ?: obj
    }

    fun getSaveId(
        @NonNull obj: Any,
        @Nullable viewModel: ViewModel?,
        @NonNull field: Field,
        id: Int
    ): Int {
        val dataBinder: Databinder =
            QuickBind.BINDEDMAP[getRealObj(obj, viewModel).javaClass] ?: return id
        val value: Any? = dataBinder.getFieldValue(field.name + "BindViewId")
        return if (value is Int) {
            value
        } else id
    }

    @Nullable
    fun getSaveIdList(
        @NonNull obj: Any,
        @Nullable viewModel: ViewModel?,
        @NonNull field: Field
    ): ArrayList<Int>? {
        val dataBinder: Databinder =
            QuickBind.BINDEDMAP[getRealObj(obj, viewModel).javaClass] ?: return null
        val value: Any? = dataBinder.getFieldValue(field.name)
        return if (value is ArrayList<*>) {
            value as ArrayList<Int>
        } else null
    }

    fun getContext(obj: Any?): Context? {
        if (obj == null) return null
        var context: Context? = null
        if (obj is Activity) {
            context = obj
        } else if (obj is Fragment) {
            context = obj.context
        } else if (obj is Dialog) {
            context = obj.context
        }
        return context
    }

    fun getRootView(obj: Any): View? {
        var rootView: View? = null
        if (obj is Activity) {
            rootView = obj.window.decorView
        }
        if (obj is Fragment) {
            rootView = obj.view
        }
        if (obj is Dialog) {
            rootView = obj.window!!.decorView
        }
        if (obj is RecyclerView.ViewHolder) {
            rootView = obj.itemView
        }
        return rootView
    }

    fun findView(obj: Any, id: Int): View? {
        var findView: View? = null
        val rootView = getRootView(obj)
        if (rootView != null) {
            findView = rootView.findViewById(id)
        }
        return findView
    }

    /**
     * @return 是否已经处理过
     */
    fun dealField(
        obj: Any,
        viewModel: ViewModel?,
        field: Field
    ): Boolean
}