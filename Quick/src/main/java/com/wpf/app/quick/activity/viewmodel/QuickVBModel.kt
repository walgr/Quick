package com.wpf.app.quick.activity.viewmodel

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.wpf.app.quicknetwork.base.RequestCoroutineScope
import kotlinx.coroutines.Job

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickVBModel<T : ViewDataBinding> : ViewModel(), LifecycleObserver, ViewLifecycle, RequestCoroutineScope {
    var mViewBinding: T? = null

    override var jobManager: MutableList<Job> = mutableListOf()

    abstract fun onBindingCreated(view: T)

    fun getViewBinding(): T {
        return mViewBinding!!
    }

    fun getContext(): Context? {
        return mViewBinding?.root?.context
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelJob()
    }
}