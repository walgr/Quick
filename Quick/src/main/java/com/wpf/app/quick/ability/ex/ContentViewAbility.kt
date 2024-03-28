package com.wpf.app.quick.ability.ex

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import com.wpf.app.quick.activity.QuickView
import com.wpf.app.quickutil.bind.RunOnContext
import com.wpf.app.quickutil.bind.runOnContext
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.helper.wrapContentHeightParams
import com.wpf.app.quickutil.helper.wrapContentWidthParams
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickwidget.quickview.QuickViewGroup

fun View.smartLayoutParams(layoutParams: LayoutParams = matchLayoutParams): LayoutParams {
    return if (this is LinearLayout) {
        if (this.orientation == LinearLayout.VERTICAL) {
            wrapContentHeightParams
        } else {
            wrapContentWidthParams
        }
    } else layoutParams
}

fun contentView(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    builder: (QuickView.() -> Unit)? = null,
): MutableList<QuickActivityAbility> {
    return setContentViewCommon(layoutId, layoutView, layoutViewInContext) {
        builder?.invoke(this)
    }
}

inline fun <reified T : ViewGroup> contentView(
    noinline builder: (T.(cur: QuickView) -> Unit)? = null,
): MutableList<QuickActivityAbility> {
    return contentViewWithSelf<QuickView, T>(builder)
}

inline fun <reified Self : QuickView, reified T : ViewGroup> contentViewWithSelf(
    noinline builder: (T.(cur: Self) -> Unit)? = null,
): MutableList<QuickActivityAbility> {
    return setContentView(layoutViewInContext = runOnContext {
        object : QuickViewGroup<T>(it, addToParent = true, forceGenerics = true) {}
    }) {
        val childView: T = it.forceTo<QuickViewGroup<T>>().shadowView!!.forceTo()
        childView.asTo<LinearLayout>()?.orientation = LinearLayout.VERTICAL
        builder?.invoke(childView, this.forceTo())
        return@setContentView childView
    }
}

fun ViewGroup.myLayout(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    layoutParams: LayoutParams = smartLayoutParams(),
    builder: (ViewGroup.() -> Unit)? = null,
) {
    addView(InitViewHelper.init(context, layoutId, layoutView, layoutViewInContext), layoutParams)
    builder?.invoke(this)
}