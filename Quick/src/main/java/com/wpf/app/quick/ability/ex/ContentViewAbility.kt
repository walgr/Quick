package com.wpf.app.quick.ability.ex

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import com.wpf.app.base.QuickView
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.bind.QuickBindWrap
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickutil.run.RunOnContext
import com.wpf.app.quickutil.run.runOnContext
import com.wpf.app.quickwidget.quickview.QuickViewGroup

fun contentView(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    builder: (QuickView.() -> Unit)? = null,
): MutableList<QuickAbility> {
    return generateContentViewCommon(layoutId, layoutView, layoutViewInContext) {
        builder?.invoke(this)
    }
}

inline fun <reified T : ViewGroup> contentView(
    noinline builder: (T.(cur: QuickView) -> Unit)? = null,
): MutableList<QuickAbility> {
    return contentViewWithSelf<QuickView, T>(builder = builder)
}

inline fun <reified Self : QuickView, reified T : ViewGroup> contentViewWithSelf(
    noinline builder: (T.(cur: Self) -> Unit)? = null,
): MutableList<QuickAbility> {
    return generateContentView(layoutViewInContext = runOnContext {
        object : QuickViewGroup<T>(this, addToParent = true, forceGenerics = true) {}
    }, generateContentView = {
        val childView: T = it.forceTo<QuickViewGroup<T>>().shadowView!!.forceTo()
        childView.asTo<LinearLayout>()?.orientation = LinearLayout.VERTICAL
        QuickBindWrap.bind(this)
        builder?.invoke(childView, this.forceTo())
        return@generateContentView childView
    })
}