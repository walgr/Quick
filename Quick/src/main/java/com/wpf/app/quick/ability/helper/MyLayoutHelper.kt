package com.wpf.app.quick.ability.helper

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.wpf.app.base.ability.helper.addView
import com.wpf.app.base.ability.scope.ContextScope
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.base.ability.scope.ViewScope
import com.wpf.app.base.ability.scope.createViewGroupScope
import com.wpf.app.base.ability.scope.createViewScope
import com.wpf.app.quick.ability.ex.viewCreateConvert
import com.wpf.app.quickbind.utils.context
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.removeParent
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickutil.widget.QuickViewGroup

inline fun <reified T : ViewGroup> Any.myLayout(
    layoutParams: ViewGroup.LayoutParams = matchWrapLayoutParams(),
    noinline builder: (ViewGroupScope<T>.() -> Unit)? = null,
): T {
    val mContext = context()!!
    val view = object : QuickViewGroup<T>(
        context = mContext,
        addToParent = true,
        forceGenerics = true
    ) {}.shadowView ?: return null!!
    view.layoutParams = layoutParams
    builder?.invoke(createViewGroupScope(view.forceTo()))
    view.let {
        view.removeParent()
        addView(view, layoutParams)
    }
    return view.forceTo()
}

fun Any.myLayout(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewCreate: (ContextScope.() -> View)? = null,
    layoutParams: ViewGroup.LayoutParams = matchLayoutParams(),
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