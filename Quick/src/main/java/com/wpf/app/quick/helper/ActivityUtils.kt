package com.wpf.app.quick.helper

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.fragment.app.Fragment
import com.wpf.app.quick.activity.QuickBaseActivity
import com.wpf.app.quick.activity.QuickBaseFragment
import com.wpf.app.quickutil.helper.initIntent

/**
 * Created by 王朋飞 on 2022/6/15.
 */

fun <T : Activity> Context.startActivity(
    activityCls: Class<T>,
    data: Bundle? = null,
    resultCallback: ActivityResultCallback<ActivityResult>? = null,
) {
    val intent = initIntent(this, activityCls, data)
    if (resultCallback != null) {
        if (this is QuickBaseActivity) {
            this.registerForActivityResult(resultCallback)
            this.launcher?.launch(intent)
        }
    } else {
        startActivity(intent)
    }
}

inline fun <reified T : Activity> Context.startActivity(
    data: Bundle? = null,
    resultCallback: ActivityResultCallback<ActivityResult>? = null,
) {
    this.startActivity(T::class.java, data, resultCallback)
}

fun <T : Activity> Fragment.startActivity(
    activityCls: Class<T>,
    data: Bundle? = null,
    resultCallback: ActivityResultCallback<ActivityResult>? = null,
) {
    val intent = initIntent(this.requireContext(), activityCls, data)
    if (resultCallback != null) {
        if (this is QuickBaseFragment) {
            this.registerForActivityResult(resultCallback)
            this.launcher?.launch(intent)
        }
    } else {
        startActivity(intent)
    }
}

inline fun <reified T : Activity> Fragment.startActivity(
    data: Bundle? = null,
    resultCallback: ActivityResultCallback<ActivityResult>? = null,
) {
    this.startActivity(T::class.java, data, resultCallback)
}