package com.wpf.app.quickrecyclerview.utils

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBinderMapper
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.getBinding
import androidx.databinding.ViewDataBinding

fun View.findBinding(bindClass: Class<out ViewDataBinding>? = null): View? {
    if (bindClass != null) {
        if (QuickDataBindingUtil.bind<ViewDataBinding>(this)?.javaClass?.superclass == bindClass) return this
    } else {
        if (QuickDataBindingUtil.bind<ViewDataBinding>(this) != null) return this
    }
    if (this is ViewGroup) {
        for (it in 0 until this.childCount) {
            val child = getChildAt(it)
            if (bindClass != null) {
                if (QuickDataBindingUtil.bind<ViewDataBinding>(child)?.javaClass?.superclass == bindClass) return child
            } else {
                if (QuickDataBindingUtil.bind<ViewDataBinding>(child) != null) return child
            }
        }
        for (it in 0 until this.childCount) {
            return getChildAt(it).findBinding(bindClass)
        }
    }
    return null
}

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