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
        mParent: ViewGroup? = null,
        attachToRoot: Boolean = false
    ): View {
        return layoutViewCreate?.invoke(context) ?: layoutView ?: layoutId.toView(context, mParent, attachToRoot)
    }

    inline fun <reified T: View> newInstance(context: Context): T {
        return T::class.java.getConstructor(Context::class.java).newInstance(context)
    }
}