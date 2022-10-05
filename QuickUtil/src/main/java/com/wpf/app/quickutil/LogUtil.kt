package com.wpf.app.quickutil

import android.util.Log

/**
 * Created by 王朋飞 on 2022/8/31.
 *
 */
object LogUtil {
    private val tag = "Quick"

    fun e(msg: String) {
        Log.e(tag, msg)
    }
}