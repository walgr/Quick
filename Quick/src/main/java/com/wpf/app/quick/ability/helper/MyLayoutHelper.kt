package com.wpf.app.quick.ability.helper

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.removeParent
import com.wpf.app.quickutil.other.context
import com.wpf.app.quickutil.run.RunOnContext
import com.wpf.app.quickutil.widget.QuickViewGroup
import com.wpf.app.quickutil.widget.smartLayoutParams

inline fun <reified T : ViewGroup> Any.myLayout(
    layoutParams: ViewGroup.LayoutParams = if (this is ViewGroup) smartLayoutParams(
        matchWrapLayoutParams()
    ) else matchWrapLayoutParams(),
    noinline builder: (T.() -> Unit)? = null,
): T {
    val mContext = context()!!
    val view = object : QuickViewGroup<T>(
        context = mContext,
        addToParent = true,
        forceGenerics = true
    ) {}.shadowView
    view?.layoutParams = layoutParams
    if (this is ViewGroup) {
        view?.let {
            view.removeParent()
            this.addView(view, layoutParams)
        }
    }
    builder?.invoke(view as T)
    return view as T
}

fun Any.myLayout(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    layoutParams: ViewGroup.LayoutParams = if (this is ViewGroup) smartLayoutParams() else matchLayoutParams(),
    builder: (View.() -> Unit)? = null,
): View {
    val mContext = context()!!
    val view = InitViewHelper.init(mContext, layoutId, layoutView, layoutViewInContext)
    if (this is ViewGroup) {
        addView(view, layoutParams)
        builder?.invoke(this)
    } else {
        builder?.invoke(view)
    }
    return view
}