package com.wpf.app.quickbind.plugins

import androidx.lifecycle.ViewModel
import com.wpf.app.quick.annotations.bind.GroupView
import com.wpf.app.quickbind.utils.GroupViews
import java.lang.reflect.Field

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class GroupViewPlugin : BindBasePlugin {

    override fun dealField(
        obj: Any,
        viewModel: ViewModel?,
        field: Field
    ) {
        val groupViewA = field.getAnnotation(
            GroupView::class.java
        ) ?: return
        field.isAccessible = true
        val groupViews = GroupViews()
        val r2IdList = getSaveIdList(obj, viewModel, field)
        if (r2IdList != null) {
            for (id in r2IdList) {
                val findView = findView(obj, id)
                if (findView != null) {
                    groupViews.viewList.add(findView)
                }
            }
        } else {
            for (id in groupViewA.idList) {
                val findView = findView(obj, id)
                if (findView != null) {
                    groupViews.viewList.add(findView)
                }
            }
        }
        try {
            if (viewModel != null) {
                field[viewModel] = groupViews
            } else {
                field[obj] = groupViews
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return
    }
}