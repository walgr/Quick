package com.wpf.app.quickutil.helper

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

object InitViewHelper {
    fun init(
        context: Context,
        @LayoutRes layoutId: Int = 0,
        layoutView: View? = null,
        layoutViewCreate: (Context.() -> View)? = null,
    ): View {
        return layoutViewCreate?.invoke(context) ?: layoutView ?: layoutId.toView(context)
    }

    inline fun <reified T: ViewGroup> newInstance(context: Context): T {
        return T::class.java.getConstructor(Context::class.java).newInstance(context)
    }
}