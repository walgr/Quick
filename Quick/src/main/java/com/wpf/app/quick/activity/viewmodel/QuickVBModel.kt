package com.wpf.app.quick.activity.viewmodel

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.wpf.app.base.Quick
import kotlinx.coroutines.Job

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickVBModel<T : Quick, VB : ViewDataBinding> : QuickViewModel<T>() {
    private var mViewBinding: VB? = null

    override var jobManager: MutableList<Job> = mutableListOf()

    open fun onBindingCreated(view: VB) {

    }

    fun getViewBinding(): VB {
        return mViewBinding!!
    }

    fun setViewBinding(viewBinding: VB) {
        this.mViewBinding = viewBinding
    }

    fun getContext(): Context? {
        return mViewBinding?.root?.context
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelJob()
    }
}