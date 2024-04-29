package com.wpf.app.quick.helper

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.DataBinderMapper
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.getBinding
import androidx.databinding.ViewDataBinding

@SuppressLint("RestrictedApi")
object QuickDataBindingUtil {
    private val sMapper: DataBinderMapper by lazy {
        DataBindingUtil::class.java.getDeclaredField("sMapper").apply { isAccessible = true }
            .get(null) as DataBinderMapper
    }

    fun <T : ViewDataBinding> bind(root: View): T? {
        val binding = getBinding<T>(root)
        return if (binding != null) {
            binding
        } else {
            val tagObj = root.tag
            if (tagObj !is String) {
                null
            } else {
                val layoutId = sMapper.getLayoutId(tagObj)
                if (layoutId == 0) {
                    null
                } else {
                    sMapper.getDataBinder(null, root, layoutId) as T
                }
            }
        }
    }
}