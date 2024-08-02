package com.wpf.app.quickutil.ability.helper

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.wpf.app.quickutil.ability.scope.*
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.generic.context
import com.wpf.app.quickutil.helper.generic.forceTo
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickutil.helper.matchWrapMarginLayoutParams
import com.wpf.app.quickutil.helper.removeParent

inline fun <reified T : ViewGroup> Any.viewGroupCreate(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
    noinline builder: (ViewGroupScope<T>.() -> Unit)? = null,
): T {
    val mContext = context()!!
    val view = T::class.java.getConstructor(Context::class.java).newInstance(mContext)
    view.layoutParams = layoutParams
    builder?.invoke(view.createViewGroupScope())
    view.let {
        view.removeParent()
        addView(view)
    }
    return view.forceTo()
}

fun Any.viewGroupCreate(
    layoutParams: ViewGroup.LayoutParams = matchMarginLayoutParams(),
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewCreate: (ContextScope.() -> View)? = null,
    builder: (ViewScope<View>.() -> Unit)? = null,
): View {
    val mContext = context()!!
    val view = InitViewHelper.init(mContext, layoutId, layoutView, viewCreateConvert(layoutViewCreate))
    when (this) {
        is ViewGroupScope<out ViewGroup> -> {
            builder?.invoke(this.view.createViewScope())
        }

        is ViewGroup -> {
            builder?.invoke(this.createViewScope())
        }

        else -> {
            builder?.invoke(view.createViewScope())
        }
    }
    addView(view, layoutParams)
    return view
}

fun Any.vLinearLayout(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
    builder: (ViewGroupScope<LinearLayout>.() -> Unit)? = null,
): LinearLayout {
    return viewGroupCreate<LinearLayout>(layoutParams = layoutParams) {
        orientation(LinearLayout.VERTICAL)
        builder?.invoke(this)
    }
}

fun Any.vLinearLayoutScope(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
): ViewGroupScope<LinearLayout> {
    return viewGroupCreate<LinearLayout>(layoutParams = layoutParams) {
        orientation(LinearLayout.VERTICAL)
    }.createViewGroupScope()
}

fun Any.hLinearLayout(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
    builder: (ViewGroupScope<LinearLayout>.() -> Unit)? = null,
): LinearLayout {
    return viewGroupCreate<LinearLayout>(layoutParams = layoutParams) {
        orientation(LinearLayout.HORIZONTAL)
        builder?.invoke(this)
    }
}

fun Any.hLinearLayoutScope(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
): ViewGroupScope<LinearLayout> {
    return viewGroupCreate<LinearLayout>(layoutParams = layoutParams) {
        orientation(LinearLayout.HORIZONTAL)
    }.createViewGroupScope()
}

fun Any.relativeLayout(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
    builder: (ViewGroupScope<RelativeLayout>.() -> Unit)? = null,
): RelativeLayout {
    return viewGroupCreate<RelativeLayout>(layoutParams = layoutParams) {
        builder?.invoke(this)
    }
}

fun Any.relativeLayoutScope(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
): ViewGroupScope<RelativeLayout> {
    return viewGroupCreate<RelativeLayout>(layoutParams = layoutParams).createViewGroupScope()
}

fun Any.frameLayout(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
    builder: (ViewGroupScope<FrameLayout>.() -> Unit)? = null,
): FrameLayout {
    return viewGroupCreate<FrameLayout>(layoutParams = layoutParams) {
        builder?.invoke(this)
    }
}

fun Any.frameLayoutScope(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
): ViewGroupScope<FrameLayout> {
    return viewGroupCreate<FrameLayout>(layoutParams = layoutParams).createViewGroupScope()
}

fun Any.constraintLayout(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
    builder: (ViewGroupScope<ConstraintLayout>.() -> Unit)? = null,
): ConstraintLayout {
    return viewGroupCreate<ConstraintLayout>(layoutParams = layoutParams) {
        builder?.invoke(this)
    }
}

fun Any.constraintLayoutScope(
    layoutParams: ViewGroup.LayoutParams = matchWrapMarginLayoutParams(),
): ViewGroupScope<ConstraintLayout> {
    return viewGroupCreate<ConstraintLayout>(layoutParams = layoutParams).createViewGroupScope()
}