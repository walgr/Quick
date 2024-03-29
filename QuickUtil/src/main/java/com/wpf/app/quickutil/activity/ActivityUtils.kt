package com.wpf.app.quickutil.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.wpf.app.quickutil.other.forceTo
import java.io.Serializable
import java.util.ArrayList

/**
 * Created by 王朋飞 on 2022/6/15.
 */
fun <T : Activity> initIntent(
    context: Context,
    activityCls: Class<T>,
    data: Map<String, Any?>? = null,
): Intent {
    val intent = Intent(context, activityCls)
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
    return intent
}

fun <T : Activity> Context.startActivity(
    activityCls: Class<T>,
    data: Map<String, Any?>? = null,
) {
    startActivity(initIntent(this, activityCls, data))
}

inline fun <reified T : Activity> Context.startActivity(
    data: Map<String, Any?>? = null,
) {
    startActivity(T::class.java, data)
}

inline fun <reified T : Activity> Fragment.startActivity(
    activityCls: Class<T>,
    data: Map<String, Any?>? = null,
) {
    startActivity(initIntent(this.requireContext(), activityCls, data))
}

inline fun <reified T : Activity> Fragment.startActivity(
    data: Map<String, Any?>? = null,
) {
    startActivity(T::class.java, data)
}

fun Activity.contentView(): View {
    return findViewById(android.R.id.content)
}

fun Activity.myContentView(): View {
    return findViewById<ViewGroup>(android.R.id.content)[0]
}

fun View.activity(): Activity = context as Activity

inline fun <reified T : Activity> View.activityForce(): T = context.forceTo<T>()