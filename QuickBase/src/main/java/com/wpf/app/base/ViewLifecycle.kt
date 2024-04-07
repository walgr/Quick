package com.wpf.app.base

import android.content.Intent
import android.os.Bundle

interface ViewLifecycle {
    fun onCreate() {}

    fun onResume() {}

    fun onPause() {}

    fun onStop() {}

    fun onDestroy() {}

    fun onSaveInstanceState(outState: Bundle) {}

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}
}