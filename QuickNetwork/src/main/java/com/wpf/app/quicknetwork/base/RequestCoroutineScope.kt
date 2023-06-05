package com.wpf.app.quicknetwork.base

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

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