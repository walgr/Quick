package com.wpf.app.quickutil.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

fun <T : Activity> initIntent(
    context: Context,
    activityCls: Class<T>,
    data: Bundle? = null,
): Intent {
    val intent = Intent(context, activityCls)
    if (data != null) {
        intent.putExtras(data)
    }
    return intent
}

fun <T : Activity> Context.startActivity(
    activityCls: Class<T>,
    data: Bundle? = null,
) {
    startActivity(initIntent(this, activityCls, data))
}

inline fun <reified T : Activity> Context.startActivity(
    data: Bundle? = null,
) {
    startActivity(T::class.java, data)
}

inline fun <reified T : Activity> Fragment.startActivity(
    activityCls: Class<T>,
    data: Bundle? = null,
) {
    startActivity(initIntent(this.requireContext(), activityCls, data))
}

inline fun <reified T : Activity> Fragment.startActivity(
    data: Bundle? = null,
) {
    startActivity(T::class.java, data)
}