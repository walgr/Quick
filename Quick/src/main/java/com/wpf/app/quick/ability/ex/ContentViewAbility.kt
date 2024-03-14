package com.wpf.app.quick.ability.ex

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import com.wpf.app.quick.ability.QuickActivityAbility
import com.wpf.app.quick.ability.setContentView
import com.wpf.app.quickutil.bind.RunOnContext
import com.wpf.app.quickutil.bind.runOnContext
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.warpContentHeightParams
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickwidget.quickview.QuickViewGroup

fun contentView(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
): MutableList<QuickActivityAbility> {
    return setContentView(layoutId, layoutView, layoutViewInContext = layoutViewInContext)
}

inline fun <reified T : ViewGroup> contentView(
    noinline builder: (T.() -> Unit)? = null
): MutableList<QuickActivityAbility> {
    return setContentView(
        layoutViewInContext = runOnContext {
            object : QuickViewGroup<T>(it, addToParent = true, forceGenerics = true) {}
        }) {
        val childView: T = it.forceTo<QuickViewGroup<T>>().shadowView!!.forceTo()
        childView.asTo<LinearLayout>()?.orientation = LinearLayout.VERTICAL
        builder?.invoke(childView)
        return@setContentView childView
    }
}

fun LinearLayout.myLayout(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    layoutParams: ViewGroup.LayoutParams = warpContentHeightParams
) {
    this.forceTo<ViewGroup>().myLayout(layoutId, layoutView, layoutViewInContext, layoutParams)
}

fun ViewGroup.myLayout(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    layoutParams: ViewGroup.LayoutParams = matchWrapLayoutParams
) {
    addView(InitViewHelper.init(context, layoutId, layoutView, layoutViewInContext), layoutParams)
}