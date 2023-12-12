package com.wpf.app.quickbind.plugins

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quick.annotations.bind.BindD2VHHelper
import com.wpf.app.quick.annotations.bind.BindData2View
import com.wpf.app.quickutil.bind.RunOnHolderWithSelf
import com.wpf.app.quickbind.utils.ReflectHelper
import java.lang.reflect.Field

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class BindData2ViewPlugin : BindBasePlugin {

    override fun dealField(
        obj: Any,
        viewModel: ViewModel?,
        field: Field
    ) {
        try {
            val bindData2View = field.getAnnotation(BindData2View::class.java)
                ?: return
            val bindId: Int = bindData2View.id
            val helper = bindData2View.helper.java as Class<BindD2VHHelper<RecyclerView.ViewHolder, View, Any>>
            var viewParent: Any? = obj
            var findView: View? = getRootView(obj)
            if (parentClassIs(obj.javaClass, "QuickBindData")) {
                viewParent = obj.javaClass.getMethod("getViewHolder").invoke(obj)
                if (viewParent is RecyclerView.ViewHolder) {
                    findView = viewParent.itemView
                }
            }
            if (viewParent == null) return
            if (bindId != 0) {
                val id = getSaveId(obj, viewModel, field, bindId)
                findView = findView(viewParent, id)
            }
            field.isAccessible = true
            val value = field[getRealObj(obj, viewModel)]
            if (findView == null || value == null) return
            var bindBaseHelper: BindD2VHHelper<RecyclerView.ViewHolder, View, Any>?
            try {
                helper.getField("INSTANCE").let {
                    bindBaseHelper = it.get(getRealObj(obj, viewModel)) as? BindD2VHHelper<RecyclerView.ViewHolder, View, Any>
                }
            } catch (ignore: Exception) {
                bindBaseHelper = helper.getDeclaredConstructor().newInstance()
            }
            if (bindBaseHelper == null) {
                bindBaseHelper = helper.getDeclaredConstructor().newInstance()
            }
            if (value is RunOnHolderWithSelf<*, *>) {
                bindBaseHelper?.initView(viewParent as? RecyclerView.ViewHolder,
                    findView, (value as RunOnHolderWithSelf<Any, Any>).run(findView, obj))
            } else {
                bindBaseHelper?.initView(viewParent as? RecyclerView.ViewHolder, findView, value)
            }
            return
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return
    }

    private fun parentClassIs(cur: Class<*>, parentName: String): Boolean {
        var curCls = cur
        while (parentName != curCls.simpleName) {
            curCls = curCls.superclass as Class<*>
            if (ReflectHelper.canBreakScan(curCls)) return false
            if (curCls.simpleName == parentName) {
                return true
            }
        }
        return true
    }
}