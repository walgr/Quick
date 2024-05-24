package com.wpf.app.base.ability.helper

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.wpf.app.base.ability.scope.ContextScope
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.base.ability.scope.ViewScope
import com.wpf.app.base.ability.scope.createViewGroupScope
import com.wpf.app.base.ability.scope.createViewScope
import com.wpf.app.base.utils.context
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickutil.helper.matchWrapMarginLayoutParams
import com.wpf.app.quickutil.helper.removeParent
import com.wpf.app.quickutil.other.forceTo

inline fun <reified T : ViewGroup> Any.viewGroupCreate(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
    noinline builder: (ViewGroupScope<T>.() -> Unit)? = null,
): T {
    val mContext = context()!!
    val view = T::class.java.getConstructor(Context::class.java).newInstance(mContext)
    view.layoutParams = layoutParams
    builder?.invoke(createViewGroupScope(view))
    view.let {
        view.removeParent()
        addView(view)
    }
    return view.forceTo()
}

fun Any.viewGroupCreate(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewCreate: (ContextScope.() -> View)? = null,
    layoutParams: ViewGroup.LayoutParams = matchMarginLayoutParams(),
    builder: (ViewScope<View>.() -> Unit)? = null,
): View {
    val mContext = context()!!
    val view = InitViewHelper.init(mContext, layoutId, layoutView, viewCreateConvert(layoutViewCreate))
    when (this) {
        is ViewGroupScope<out ViewGroup> -> {
            builder?.invoke(createViewScope(this.view))
        }

        is ViewGroup -> {
            builder?.invoke(createViewScope(this))
        }

        else -> {
            builder?.invoke(createViewScope(view))
        }
    }
    addView(view, layoutParams)
    return view
}