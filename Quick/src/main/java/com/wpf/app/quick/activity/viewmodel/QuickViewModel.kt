package com.wpf.app.quick.activity.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.wpf.app.base.ViewLifecycle
import com.wpf.app.base.QuickView
import com.wpf.app.quicknetwork.base.RequestCoroutineScope
import kotlinx.coroutines.Job

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickViewModel<T : QuickView> : ViewModel(), LifecycleObserver, ViewLifecycle, RequestCoroutineScope {
    override var jobManager: MutableList<Job> = mutableListOf()
    var quickView: T? = null
    open fun onViewCreated(baseView: T) {

    }

    override fun onDestroy() {
        super.onDestroy()
        cancelJob()
    }
}