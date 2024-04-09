package com.wpf.app.quickbind.utils

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import android.widget.TextView
import com.wpf.app.quickbind.annotations.BindD2VHelper
import com.wpf.app.quickutil.run.RunOnViewWithSelf
import com.wpf.app.quickutil.helper.allChild
import com.wpf.app.quickutil.other.forceTo

object DataAutoSet2ViewUtils {

    private var bind2ViewList: MutableList<BindD2VHelper<View, Any>> = arrayListOf()

    fun <T: Any> autoSet(data: T, view: View) {
        setData2ViewGroup(view, data)
    }

    private fun setData2ViewGroup(view: View, data: Any) {
        if (view is ViewGroup) {
            view.allChild().forEach {
                if (it is ViewGroup) {
                    setData2ViewGroup(it, data)
                } else {
                    setData2View(it, data)
                }
            }
        } else {
            setData2View(view, data)
        }
    }

    private fun setData2View(view: View, data: Any) {
        val viewId = view.id
        var viewIdName = ""
        kotlin.runCatching {
            if (viewId > 0) {
                viewIdName = view.context.resources.getResourceEntryName(viewId)
            }
        }
        if (TextUtils.isEmpty(viewIdName)) return
        var viewData: Any? = null
        kotlin.runCatching {
            data.javaClass.getDeclaredField(viewIdName).let {
                it.isAccessible = true
                val fieldValue = it.get(data)
                viewData = if (fieldValue is RunOnViewWithSelf<*, *>) {
                    (fieldValue.forceTo<RunOnViewWithSelf<Any, Any>>()).run(view, data)
                } else {
                    fieldValue
                }
            }
        }
        if (viewData == null) return
        when (view) {
            is TextView -> {
                view.text = viewData.toString()
            }

            is Checkable -> {
                view.isChecked = viewData == true
            }
        }
        bind2ViewList.forEach { bind ->
            bind.initView(view, data)
        }
    }
}

interface BindData2View {
    fun bind(view: View, data: Any)
}