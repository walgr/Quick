package com.wpf.app.quickutil.helper

import android.app.Activity
import android.app.Dialog
import android.view.ContextThemeWrapper

fun Dialog.getRealActivity(): Activity? {
    if (context is Activity) {
        return context as Activity
    } else if (context is ContextThemeWrapper) {
        if ((context as ContextThemeWrapper).baseContext is Activity) {
            return (context as ContextThemeWrapper).baseContext as Activity
        } else if ((context as ContextThemeWrapper).baseContext is ContextThemeWrapper) {
            if (((context as ContextThemeWrapper).baseContext as ContextThemeWrapper).baseContext is Activity) {
                return ((context as ContextThemeWrapper).baseContext as ContextThemeWrapper).baseContext as Activity
            }
        }
    }
    return null
}