package com.wpf.app.quick.helper

import android.view.View
import androidx.databinding.DataBinderMapper
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.getBinding
import androidx.databinding.ViewDataBinding

object QuickDataBindingUtil {
    private val sMapper: DataBinderMapper by lazy {
        DataBindingUtil::class.java.getDeclaredField("sMapper").apply { isAccessible = true }.get(null) as DataBinderMapper
    }

    fun <T : ViewDataBinding> bind(root: View): T? {
        val binding = getBinding<T>(root)
        if (binding != null) {
            return binding
        } else {
            val tagObj = root.tag
            if (tagObj !is String) {
                return null
            } else {
                val layoutId = sMapper.getLayoutId(tagObj)
                if (layoutId == 0) {
                    return null
                } else {
                    return sMapper.getDataBinder(null, root, layoutId) as T
                }
            }
        }
    }
}