package com.wpf.app.quickutil.helper

import android.os.Handler
import android.os.Looper
import com.wpf.app.quickutil.helper.generic.asTo

typealias DoLastRun<T> = MutableSet<T>.() -> Unit
class DoLast<T> {
    class RunnableTask<T>(
        var runnable: DoLastRun<T>,
        var noDoList: MutableSet<T>,
    )

    private val mHandle: Handler = Handler(Looper.getMainLooper()) {
        if (it.what == 0x01) {
            it.obj.asTo<RunnableTask<T>>()?.apply {
                runnable.invoke(noDoList)
                noDoList.clear()
            }
            return@Handler true
        }
        false
    }
    private var runnableTask: RunnableTask<T>? = null
    private val noDoList: MutableSet<T> = mutableSetOf()
    fun event(
        maxTime: Long,
        noDoListRun: () -> List<T>,
        runnable: DoLastRun<T>,
    ) {
        if (maxTime <= 0) return
        noDoList.addAll(noDoListRun.invoke())
        mHandle.removeCallbacksAndMessages(null)
        if (runnableTask == null) {
            runnableTask = RunnableTask(runnable, noDoList)
        } else {
            runnableTask?.runnable = runnable
        }
        mHandle.sendMessageDelayed(mHandle.obtainMessage(0x01, runnableTask), maxTime)
    }
}