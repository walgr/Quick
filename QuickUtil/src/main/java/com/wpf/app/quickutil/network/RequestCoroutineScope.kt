package com.wpf.app.quickutil.network

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Created by 王朋飞 on 2022/7/22.
 * 注解使用此类，移动需要修改注解代码
 */
interface RequestCoroutineScope: CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    var jobManager: MutableList<Job>

    fun addJob(job: Job) {
        jobManager.add(job)
    }

    fun removeJob(job: Job) {
        jobManager.remove(job)
    }

    fun cancelJob(job: Job? = null, cause: CancellationException? = null) {
        if (job == null) {
            jobManager.forEach {
                it.cancel(cause)
            }
            jobManager.clear()
        } else {
            job.cancel(cause)
            jobManager.remove(job)
        }
    }
}