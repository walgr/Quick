package com.wpf.app.quick.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.wpf.app.quick.activity.QuickView

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickViewModel<T : QuickView> : ViewModel() {
    var baseView: T? = null

    fun onResume() {}

    fun onPause() {}

    fun onStop() {}

    fun onDestroy() {}

    abstract fun onViewCreated(baseView: T)
}