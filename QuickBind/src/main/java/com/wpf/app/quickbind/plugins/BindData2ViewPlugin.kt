package com.wpf.app.quickbind.plugins

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quick.annotations.BindD2VHelper
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.annotations.internal.Constants
import com.wpf.app.quickbind.interfaces.runOnHolder
import com.wpf.app.quickbind.utils.ReflectHelper
import java.lang.reflect.Field
import kotlin.reflect.KClass

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class BindData2ViewPlugin : BasePlugin {

    override fun dealField(
        obj: Any,
        viewModel: ViewModel?,
        field: Field
    ): Boolean {
        try {
            val bindData2View = field.getAnnotation(BindData2View::class.java)
                ?: return false
            val bindId: Int = bindData2View.id
            val helper = bindData2View.helper.java as Class<BindD2VHelper<View, Any>>
            var viewParent = obj
            var findView: View? = null
            if (parentClassIs(obj.javaClass, "QuickBindData")) {
                viewParent = obj.javaClass.getMethod("getViewHolder").invoke(obj) as Any
                if (viewParent is RecyclerView.ViewHolder) {
                    findView = viewParent.itemView
                }
            }
            if (bindId != Constants.NO_RES_ID) {
                val id = getSaveId(obj, viewModel, field, bindId)
                findView = findView(viewParent, id)
            }
            field.isAccessible = true
            val value = field[getRealObj(obj, viewModel)]
            if (findView == null || value == null) return true
            val bindBaseHelper = helper.newInstance()
            if (value is runOnHolder<*, *>) {
                bindBaseHelper.initView(findView, (value as runOnHolder<View, Any>).run(findView))
            } else {
                bindBaseHelper.initView(findView, value)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun parentClassIs(cur: Class<*>, parentName: String): Boolean {
        var cur = cur
        while (parentName != cur.simpleName) {
            cur = cur.superclass
            if (ReflectHelper.canBreakScan(cur)) return false
            if (cur.simpleName == parentName) {
                return true
            }
        }
        return true
    }
}