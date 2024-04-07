package com.wpf.app.quickutil.log

import android.util.Log

/**
 * Created by 王朋飞 on 2022/8/31.
 *
 */
object LogUtil {
    internal const val TAG = "Quick"

    fun e(msg: String) {
        Log.e(TAG, msg)
    }

    fun e(tag: String, msg: String) {
        Log.e(tag.ifEmpty { TAG }, msg)
    }
}