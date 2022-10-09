package com.wpf.app.quickutil

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import java.io.Serializable
import java.util.ArrayList

/**
 * Created by 王朋飞 on 2022/6/15.
 */
//object ActivityUtils {
//    fun <T : Activity?> startActivity(activity: Context, activityCls: Class<T>) {
//        startActivity(activity, activityCls, null)
//    }
//
//    fun <T : Activity?> startActivity(
//        activity: Context,
//        activityCls: Class<T>,
//        data: Map<String, Any?>?
//    ) {
//        val intent = Intent(activity, activityCls)
//        if (data != null) {
//            val keys = data.keys
//            for (key in keys) {
//                val value = data[key]
//                if (value is ArrayList<*> && value.isNotEmpty() && value[0] is Parcelable) {
//                    intent.putParcelableArrayListExtra(key, value as ArrayList<out Parcelable?>?)
//                } else if (value is Parcelable) {
//                    intent.putExtra(key, value)
//                } else if (value is Serializable) {
//                    intent.putExtra(key, value)
//                }
//            }
//        }
//        activity.startActivity(intent)
//    }
//}

fun <T : Activity> Activity.startActivity(activityCls: Class<T>) {
    startActivity(activityCls, data = null)
}

fun <T : Activity> Activity.startActivity(
    activityCls: Class<T>,
    data: Map<String, Any?>?
) {
    val intent = Intent(this, activityCls)
    if (data != null) {
        val keys = data.keys
        for (key in keys) {
            val value = data[key]
            if (value is ArrayList<*> && value.isNotEmpty() && value[0] is Parcelable) {
                intent.putParcelableArrayListExtra(key, value as ArrayList<out Parcelable>)
            } else if (value is Parcelable) {
                intent.putExtra(key, value)
            } else if (value is Serializable) {
                intent.putExtra(key, value)
            }
        }
    }
    startActivity(intent)
}