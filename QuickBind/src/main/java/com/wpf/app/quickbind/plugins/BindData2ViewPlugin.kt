package com.wpf.app.quickbind.plugins

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quick.annotations.BindD2VHelper
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.annotations.internal.Constants
import com.wpf.app.quickbind.annotations.BindD2VHHelper
import com.wpf.app.quickbind.interfaces.RunOnContext
import com.wpf.app.quickbind.interfaces.RunOnHolder
import com.wpf.app.quickbind.interfaces.RunOnHolderWithSelf
import com.wpf.app.quickbind.utils.ReflectHelper
import java.lang.reflect.Field

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
            val helper = bindData2View.helper.java as Class<BindD2VHelper<RecyclerView.ViewHolder, View, Any>>
            var viewParent: Any? = obj
            var findView: View? = getRootView(obj)
            if (parentClassIs(obj.javaClass, "QuickBindData")) {
                viewParent = obj.javaClass.getMethod("getViewHolder").invoke(obj)
                if (viewParent is RecyclerView.ViewHolder) {
                    findView = viewParent.itemView
                }
            }
            if (viewParent == null) return true
            if (bindId != Constants.NO_RES_ID) {
                val id = getSaveId(obj, viewModel, field, bindId)
                findView = findView(viewParent, id)
            }
            field.isAccessible = true
            val value = field[getRealObj(obj, viewModel)]
            if (findView == null || value == null) return true
//            Log.e("onBindViewHolder","-----" + obj)
            var bindBaseHelper: BindD2VHelper<RecyclerView.ViewHolder, View, Any>?
            try {
                helper.fields.find {
                    it.name == "INSTANCE"
                }.let {
                    bindBaseHelper = it?.get(getRealObj(obj, viewModel)) as? BindD2VHelper<RecyclerView.ViewHolder, View, Any>
                }
            } catch (ignore: Exception) {
                bindBaseHelper = helper.newInstance()
            }
            if (bindBaseHelper == null) {
                bindBaseHelper = helper.newInstance()
            }
            if (value is RunOnHolderWithSelf<*, *>) {
                bindBaseHelper?.initView(viewParent as? RecyclerView.ViewHolder,
                    findView, (value as RunOnHolderWithSelf<Any, Any>).run(findView, obj))
            } else {
                bindBaseHelper?.initView(viewParent as? RecyclerView.ViewHolder, findView, value)
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