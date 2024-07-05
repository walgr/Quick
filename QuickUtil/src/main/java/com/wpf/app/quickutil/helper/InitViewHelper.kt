package com.wpf.app.quickutil.helper

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.wpf.app.quickutil.Quick

object InitViewHelper {
    fun init(
        context: Context,
        @LayoutRes layoutId: Int = 0,
        layoutView: View? = null,
        layoutViewCreate: (Context.() -> View)? = null,
        mParent: ViewGroup? = null,
        attachToRoot: Boolean = false,
        self: Quick? = null,
        layoutViewCreateWithQuick: (Context.(self: Quick?) -> View)? = null,
    ): View {
        return layoutViewCreateWithQuick?.invoke(context, self) ?: layoutViewCreate?.invoke(context)
        ?: layoutView ?: layoutId.toView(context, mParent, attachToRoot)
    }

    inline fun <reified T : View> newInstance(context: Context): T {
        return T::class.java.getConstructor(Context::class.java).newInstance(context)
    }
}