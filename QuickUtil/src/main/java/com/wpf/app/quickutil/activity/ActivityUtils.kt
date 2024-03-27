package com.wpf.app.quickutil.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.wpf.app.quickutil.other.forceTo
import java.io.Serializable
import java.util.ArrayList

/**
 * Created by 王朋飞 on 2022/6/15.
 */
fun <T : Activity> Context.quickStartActivity(
    activityCls: Class<T>,
    data: Map<String, Any?>? = null,
    resultCallback: ActivityResultCallback<ActivityResult>? = null,
) {
    val intent = Intent(this, activityCls)
    if (data != null) {
        val keys = data.keys
        for (key in keys) {
            val value = data[key]
            if (value is ArrayList<*> && value.isNotEmpty() && value[0] is Parcelable) {
                intent.putParcelableArrayListExtra(key, value.forceTo())
            } else if (value is Parcelable) {
                intent.putExtra(key, value)
            } else if (value is Serializable) {
                intent.putExtra(key, value)
            }
        }
    }
    if (resultCallback != null) {
        if (this is AppCompatActivity) {
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                resultCallback
            ).launch(intent)
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

inline fun <reified T : Activity> Fragment.quickStartActivity(
    activityCls: Class<T>,
    data: Map<String, Any?>? = null,
    resultCallback: ActivityResultCallback<ActivityResult>? = null,
) {
    activity?.quickStartActivity(activityCls, data, resultCallback)
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