package com.wpf.app.quickdialog.listeners

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by 王朋飞 on 2022/6/21.
 */
interface QuickContext {

    fun getViewContext(): Context
    fun getRealContext(): Context? {
        return this.getRealActivity()?: getViewContext()
    }

    fun getRealActivity(): Activity? {
        if (getViewContext() is Activity) {
            return getViewContext() as Activity
        } else if (getViewContext() is ContextThemeWrapper) {
            if ((getViewContext() as ContextThemeWrapper).baseContext is Activity) {
                return (getViewContext() as ContextThemeWrapper).baseContext as Activity
            } else if ((getViewContext() as ContextThemeWrapper).baseContext is ContextThemeWrapper) {
                if (((getViewContext() as ContextThemeWrapper).baseContext as ContextThemeWrapper).baseContext is Activity) {
                    return ((getViewContext() as ContextThemeWrapper).baseContext as ContextThemeWrapper).baseContext as Activity
                }
            }
        }
        return null
    }

    fun getAppCompatActivity() : AppCompatActivity? {
        if (getViewContext() is AppCompatActivity) {
            return getViewContext() as AppCompatActivity
        } else if (getViewContext() is ContextThemeWrapper) {
            if ((getViewContext() as ContextThemeWrapper).baseContext is AppCompatActivity) {
                return (getViewContext() as ContextThemeWrapper).baseContext as AppCompatActivity
            } else if ((getViewContext() as ContextThemeWrapper).baseContext is ContextThemeWrapper) {
                if (((getViewContext() as ContextThemeWrapper).baseContext as ContextThemeWrapper).baseContext is AppCompatActivity) {
                    return ((getViewContext() as ContextThemeWrapper).baseContext as ContextThemeWrapper).baseContext as AppCompatActivity
                }
            }
        }
        return null
    }

    fun getScreenSize() : Point {
        if (getRealActivity() == null) return Point()
        val size = Point()
        getRealActivity()!!.getWindowManager().getDefaultDisplay().getSize(size)
        return size
    }

    fun getScreenRealSize() : Point {
        if (getRealActivity() == null) return Point()
        val size = Point()
        getRealActivity()!!.getWindowManager().getDefaultDisplay().getRealSize(size)
        return size
    }

    fun getScreenWidth(): Int {
        if (getRealActivity() == null) return 0
        val localDisplayMetrics = DisplayMetrics()
        getRealActivity()!!.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics)
        return localDisplayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        if (getRealActivity() == null) return 0
        val localDisplayMetrics = DisplayMetrics()
        getRealActivity()!!.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics)
        return localDisplayMetrics.heightPixels
    }

    fun getScreenRealHeight(): Int {
        if (getRealActivity() == null) return 0
        val localDisplayMetrics = DisplayMetrics()
        getRealActivity()!!.getWindowManager().getDefaultDisplay().getRealMetrics(localDisplayMetrics)
        return localDisplayMetrics.heightPixels
    }

    //必须在post里执行
    fun getViewRealHeight(curView: View): Int {
        return (curView.parent as ViewGroup).measuredHeight
    }

    fun getActivityRootViewHeight(defaultHeight: Int): Int {
        return if (getRealActivity() == null) defaultHeight else getRealActivity()!!.findViewById<View>(16908290).measuredHeight
    }

}