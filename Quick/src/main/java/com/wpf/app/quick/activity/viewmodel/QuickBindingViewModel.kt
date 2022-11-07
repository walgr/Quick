package com.wpf.app.quick.activity.viewmodel

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickBindingViewModel<T : ViewDataBinding> : ViewModel(), LifecycleObserver, ViewLifecycle {
    var mViewBinding: T? = null

    abstract fun onBindingCreated(mViewBinding: T?)

    fun getViewBinding(): T? {
        return mViewBinding
    }

    fun getContext(): Context? {
        return getViewBinding()?.root?.context
    }
}