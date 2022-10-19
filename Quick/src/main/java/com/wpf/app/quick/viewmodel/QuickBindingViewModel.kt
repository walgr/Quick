package com.wpf.app.quick.viewmodel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.wpf.app.quick.activity.QuickViewModelBindingActivity

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickBindingViewModel<T : ViewDataBinding> : ViewModel(), LifecycleObserver {
    var mViewBinding: T? = null
    @SuppressLint("StaticFieldLeak")
    var activity: QuickViewModelBindingActivity<out QuickBindingViewModel<T>, T>? = null

    open fun onResume() {}

    open fun onPause() {}

    open fun onStop() {}

    open fun onDestroy() {}

    open fun onSaveInstanceState(outState: Bundle) {}

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}

    abstract fun onBindingCreated(mViewBinding: T?)

    fun getViewBinding(): T? {
        return mViewBinding
    }
}