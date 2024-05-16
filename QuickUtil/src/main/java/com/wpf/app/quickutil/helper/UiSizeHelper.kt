package com.wpf.app.quickutil.helper

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import com.wpf.app.quickutil.init.ApplicationInitRegister
import com.wpf.app.quickutil.init.QuickInit
import java.lang.ref.SoftReference

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
    fun getStatusBarHeight(): Int {
        if (context?.get() == null) return 0
        val resources = context?.get()?.resources!!
        return resources.getDimensionPixelSize(
            resources.getIdentifier(
                "status_bar_height", "dimen", "android"
            )
        )
    }
}