package com.wpf.app.quickutil.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.DisplayCutout
import android.view.WindowManager
import com.wpf.app.quickutil.init.ApplicationInitRegister
import com.wpf.app.quickutil.init.QuickInit
import java.lang.ref.SoftReference
import kotlin.math.max

object UiSizeHelper : ApplicationInitRegister {
    override var context: SoftReference<Context>? = null

    init {
        QuickInit.register(this)
    }

    fun getDisplayMetrics(): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        if (context?.get() == null) return displayMetrics
        val windowManager: WindowManager = (context?.get()?.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }

    fun getScreeSize(): Point {
        val size = Point()
        if (context?.get() == null) return size
        val windowManager: WindowManager = (context?.get()?.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            windowManager.defaultDisplay.getSize(size)
        } else {
            val rect = windowManager.currentWindowMetrics.bounds
            size.x = rect.width()
            size.y = rect.height()
        }
        return size
    }

    fun getScreeWidth(): Int {
        return getScreeSize().x
    }

    fun getScreeHeight(): Int {
        return getScreeSize().y
    }

    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    fun getStatusBarHeight(context: Context? = null): Int {
        var newContext: Context? = context
        if (newContext == null) {
            if (this.context?.get() == null) return 0
            newContext = this.context?.get()!!
        }
        val resources = newContext.resources!!
        val id = resources.getIdentifier(
            "status_bar_height", "dimen", "android"
        )
        if (id <= 0) return 0
        val statusBarHeight = resources.getDimensionPixelSize(id)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && newContext is Activity) {
            val cutout: DisplayCutout = newContext.display?.cutout ?: return statusBarHeight
            val safeInsetTop = cutout.safeInsetTop
            val waterfallInsetTop = cutout.waterfallInsets.top
            // The status bar height should be:
            // Max(top cutout size, (status bar default height + waterfall top size))
            return max(safeInsetTop.toDouble(), (statusBarHeight + waterfallInsetTop).toDouble()).toInt()
        }
        return statusBarHeight
    }
}