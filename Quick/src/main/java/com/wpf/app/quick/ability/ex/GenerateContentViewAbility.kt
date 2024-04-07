package com.wpf.app.quick.ability.ex

import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelStoreOwner
import com.wpf.app.base.QuickView
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.QuickViewAbility
import com.wpf.app.quick.ability.ex.base.QuickInflateViewAbility
import com.wpf.app.quick.ability.ex.base.QuickLifecycleAbility
import com.wpf.app.quickutil.helper.match
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickutil.run.RunOnContext


@Deprecated("框架使用,建议使用contentView")
fun generateContentViewCommon(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    contentBuilder: (QuickView.(view: View) -> Unit)? = null,
): MutableList<QuickAbility> {
    return generateContentView(layoutId, layoutView, layoutViewInContext) {
        contentBuilder?.invoke(this, it)
        null
    }
}

@Deprecated("框架使用,建议使用contentView")
fun generateContentView(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    contentBuilder: (QuickView.(view: View) -> View?)? = null,
): MutableList<QuickAbility> {
    return mutableListOf(object : QuickInflateViewAbility {
        override fun layoutId(): Int {
            return layoutId
        }

        override fun layoutView(): View? {
            return layoutView
        }

        override fun layoutViewInContext(): RunOnContext<View>? {
            return layoutViewInContext
        }

        override fun generateContentView(owner: ViewModelStoreOwner, view: View): View {
            if (view.layoutParams != null) {
                view.layoutParams?.width = match
                view.layoutParams?.height = match
            } else {
                view.layoutParams = matchLayoutParams()
            }
            return contentBuilder?.invoke(owner.forceTo(), view) ?: view
        }
    })
}