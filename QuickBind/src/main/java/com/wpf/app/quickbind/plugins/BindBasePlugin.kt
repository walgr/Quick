package com.wpf.app.quickbind.plugins

import androidx.lifecycle.ViewModel
import com.wpf.app.quick.annotations.bind.Databinder
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.base.bind.plugins.BasePlugin
import java.lang.reflect.Field

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface BindBasePlugin : com.wpf.app.base.bind.plugins.BasePlugin {

    fun getSaveId(obj: Any, viewModel: ViewModel?, field: Field, id: Int): Int {
        val dataBinder: Databinder =
            QuickBind.bindMap[getRealObj(obj, viewModel).javaClass] ?: return id
        val value: Any? = dataBinder.getFieldValue(field.name + "BindViewId")
        return if (value is Int) {
            value
        } else id
    }

    fun getSaveIdList(obj: Any, viewModel: ViewModel?, field: Field): ArrayList<Int>? {
        val dataBinder: Databinder =
            QuickBind.bindMap[getRealObj(obj, viewModel).javaClass] ?: return null
        val value: Any? = dataBinder.getFieldValue(field.name)
        return if (value is ArrayList<*>) {
            value as ArrayList<Int>
        } else null
    }
}