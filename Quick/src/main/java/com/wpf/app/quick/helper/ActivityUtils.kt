package com.wpf.app.quick.helper

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.wpf.app.quick.ability.QuickFragment
import com.wpf.app.quick.activity.QuickBaseActivity
import com.wpf.app.quick.activity.QuickBaseFragment
import com.wpf.app.quickutil.activity.initIntent
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo

/**
 * Created by 王朋飞 on 2022/6/15.
 */

fun <T : Activity> Context.quickStartActivity(
    activityCls: Class<T>,
    data: Map<String, Any?>? = null,
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

inline fun <reified T : Activity> Context.quickStartActivity(
    data: Map<String, Any?>? = null,
    resultCallback: ActivityResultCallback<ActivityResult>? = null,
) {
    quickStartActivity(T::class.java, data, resultCallback)
}

fun <T : Activity> Fragment.quickStartActivity(
    activityCls: Class<T>,
    data: Map<String, Any?>? = null,
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

inline fun <reified T : Activity> Fragment.quickStartActivity(
    data: Map<String, Any?>? = null,
    resultCallback: ActivityResultCallback<ActivityResult>? = null,
) {
    quickStartActivity(T::class.java, data, resultCallback)
}

fun Activity.contentView(): View {
    return findViewById(android.R.id.content)
}

fun Activity.myContentView(): View {
    return findViewById<ViewGroup>(android.R.id.content)[0]
}

fun View.activity(): Activity = context as Activity

inline fun <reified T : Activity> View.activityForce(): T = context.forceTo<T>()