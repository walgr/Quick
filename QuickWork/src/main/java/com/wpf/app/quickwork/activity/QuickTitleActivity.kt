package com.wpf.app.quickwork.activity

import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.wpf.app.quick.activity.QuickAbilityActivity
import com.wpf.app.quick.activity.inflate
import com.wpf.app.quickutil.bind.Bind
import com.wpf.app.quickutil.bind.QuickBindWrap
import com.wpf.app.quickutil.bind.RunOnContext
import com.wpf.app.quickutil.bind.runOnContext
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.layoutParams
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickwork.R
import com.wpf.app.quickwork.widget.QuickTitleView

open class QuickTitleActivity @JvmOverloads constructor(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    titleName: String = "",
    titleBuilder: (QuickTitleView.() -> Unit)? = null,
    contentBuilder: (RelativeLayout.() -> Unit)? = null,
    showTitle: Boolean = true,
) : QuickAbilityActivity(
    inflate(layoutViewInContext = runOnContext { context ->
        LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = matchLayoutParams
            if (showTitle) {
                addView(QuickTitleView(context).apply {
                    id = R.id.titleView
                    setTitle(titleName)
                    titleBuilder?.invoke(this)
                }, matchWrapLayoutParams)
            }
            addView((object : RelativeLayout(context), Bind {
                override fun getView(): View {
                    return this
                }
            }).apply {
                id = R.id.contentLayout
                layoutParams = layoutParams<LinearLayout.LayoutParams>(matchLayoutParams).apply {
                    weight = 1f
                }
                if (layoutViewInContext != null || layoutView != null || layoutId != 0) {
                    addView(
                        InitViewHelper.init(context, layoutId, layoutView, layoutViewInContext),
                        matchLayoutParams
                    )
                }
                contentBuilder?.invoke(this)
                QuickBindWrap.bind(this)
            })
        }
    }), titleName = titleName
) {

    lateinit var titleView: QuickTitleView

    @CallSuper
    override fun initView(view: View) {
        titleView = view.findViewById(R.id.titleView)
        initTitle(titleView)
        initContentLayout(view.findViewById(R.id.contentLayout))
    }

    open fun initTitle(titleView: QuickTitleView) {

    }

    open fun initContentLayout(contentLayout: RelativeLayout) {

    }
}