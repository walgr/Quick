package com.wpf.app.quick.activity.viewmodel

import android.content.Intent
import android.os.Bundle

interface ViewLifecycle {

    fun onResume() {}

    fun onPause() {}

    fun onStop() {}

    fun onDestroy() {}

    fun onSaveInstanceState(outState: Bundle) {}

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}
}