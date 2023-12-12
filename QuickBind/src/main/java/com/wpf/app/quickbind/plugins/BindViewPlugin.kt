package com.wpf.app.quickbind.plugins

import androidx.lifecycle.ViewModel
import com.wpf.app.quick.annotations.bind.BindView
import java.lang.reflect.Field

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class BindViewPlugin : BindBasePlugin {

    override fun dealField(
        obj: Any,
        viewModel: ViewModel?,
        field: Field
    ) {
        try {
            val findViewA = field.getAnnotation(BindView::class.java) ?: return
            field.isAccessible = true
//            if (field[getRealObj(obj, viewModel)] != null) return true
            field[getRealObj(obj, viewModel)] = findView(obj, findViewA.value)
            return
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return
    }
}