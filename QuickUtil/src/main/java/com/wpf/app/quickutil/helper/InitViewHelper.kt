package com.wpf.app.quickutil.helper

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import com.wpf.app.quickutil.bind.RunOnContext
import com.wpf.app.quickutil.helper.toView

object InitViewHelper {
    fun init(
        context: Context,
        @LayoutRes layoutId: Int = 0,
        layoutView: View? = null,
        layoutViewInContext: RunOnContext<View>? = null,
    ): View {
        return layoutViewInContext?.run(context) ?: (layoutView ?: layoutId.toView(context))
    }
}