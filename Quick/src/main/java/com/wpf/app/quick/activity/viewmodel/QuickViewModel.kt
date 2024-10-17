package com.wpf.app.quick.activity.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.wpf.app.quickutil.ViewLifecycle
import com.wpf.app.quickutil.Quick
import com.wpf.app.quickutil.network.RequestCoroutineScope
import kotlinx.coroutines.Job

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickViewModel<T : Quick> : ViewModel(), LifecycleObserver, ViewLifecycle,
    RequestCoroutineScope {
    override var jobManager: MutableList<Job> = mutableListOf()
    var quickView: T? = null
    open fun onViewCreated(baseView: T) {

    }

    override fun onDestroy() {
        super.onDestroy()
        cancelJob()
    }
}