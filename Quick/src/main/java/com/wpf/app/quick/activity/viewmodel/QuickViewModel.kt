package com.wpf.app.quick.activity.viewmodel

import androidx.lifecycle.ViewModel
import com.wpf.app.quick.activity.QuickView

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickViewModel<T : QuickView> : ViewModel(), ViewLifecycle {
    var baseView: T? = null

    abstract fun onViewCreated(baseView: T)
}