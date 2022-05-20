package com.wpf.app.quick.base.viewmodel

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel

/**
 * Created by 王朋飞 on 2022/4/2.
 * Activity基础控制器带Binding
 */
open class BindingViewModel<T : ViewDataBinding>(
    var viewBinding: T? = null
) : ViewModel(), LifecycleObserver {

    open fun onBindingCreate(viewBinding: T?) {

    }
}