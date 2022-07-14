package com.wpf.app.quickbind.plugins

import androidx.lifecycle.ViewModel
import com.wpf.app.quick.annotations.BindView
import java.lang.reflect.Field

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class BindViewPlugin : BasePlugin {

    override fun dealField(
        obj: Any,
        viewModel: ViewModel?,
        field: Field
    ): Boolean {
        try {
            val findViewA = field.getAnnotation(BindView::class.java) ?: return false
            field.isAccessible = true
            if (field[getRealObj(obj, viewModel)] != null) {
                return true
            }
            val findView = findView(obj, findViewA.value)
            if (viewModel != null) {
                field[viewModel] = findView
            } else {
                field[obj] = findView
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}