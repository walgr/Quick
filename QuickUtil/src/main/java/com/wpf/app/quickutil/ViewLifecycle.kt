package com.wpf.app.quickutil

import android.content.Intent
import android.os.Bundle

interface ViewLifecycle {
    fun onCreate() {}

    fun onStart() {}

    fun onResume() {}

    fun onPause() {}

    fun onStop() {}

    fun onDestroy() {}

    fun onSaveInstanceState(outState: Bundle) {}
    fun onRestoredInstanceState(savedInstanceState: Bundle?) {}

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}
}