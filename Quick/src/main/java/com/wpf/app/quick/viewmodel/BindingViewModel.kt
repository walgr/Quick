package com.wpf.app.quick.viewmodel

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class BindingViewModel<T : ViewDataBinding> : ViewModel(), LifecycleObserver {
    var mViewBinding: T? = null

    abstract fun onBindingCreated(mViewBinding: T?)

    fun getViewBinding(): T? {
        return mViewBinding
    }
}