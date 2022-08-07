package com.wpf.app.quick.viewmodel

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.wpf.app.quick.activity.QuickView

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickViewModel<T : QuickView> : ViewModel() {
    var baseView: T? = null

    open fun onResume() {}

    open fun onPause() {}

    open fun onStop() {}

    open fun onDestroy() {}

    open fun onSaveInstanceState(outState: Bundle) {}

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}

    abstract fun onViewCreated(baseView: T)
}