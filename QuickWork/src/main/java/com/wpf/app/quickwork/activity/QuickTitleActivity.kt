package com.wpf.app.quickwork.activity

import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.wpf.app.quick.ability.QuickAbilityActivity
import com.wpf.app.quick.ability.dealBind
import com.wpf.app.quick.ability.inLinearLayout
import com.wpf.app.quick.ability.with
import com.wpf.app.quickutil.bind.QuickBindWrap
import com.wpf.app.quickutil.bind.RunOnContext
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.layoutParams
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickwork.R
import com.wpf.app.quickwork.widget.QuickTitleView

fun contentWithTitle(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    titleName: String = "",
    showTitle: Boolean = true,
    titleBuilder: (QuickTitleView.() -> Unit)? = null,
    contentBuilder: (RelativeLayout.() -> Unit)? = null,
) = inLinearLayout {
    if (showTitle) {
        addView(QuickTitleView(context).apply {
            id = R.id.titleView
            setTitle(titleName)
            titleBuilder?.invoke(this)
        }, matchWrapLayoutParams)
    }
    var contentView: View? = null
    addView(RelativeLayout(context).apply {
        id = R.id.contentLayout
        layoutParams = layoutParams<LinearLayout.LayoutParams>(matchLayoutParams).apply {
            weight = 1f
        }
        if (layoutViewInContext != null || layoutView != null || layoutId != 0) {
            contentView = InitViewHelper.init(context, layoutId, layoutView, layoutViewInContext)
            addView(contentView, matchLayoutParams)
        }
        contentBuilder?.invoke(this)

    })
    contentView
}

open class QuickTitleActivity @JvmOverloads constructor(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    titleName: String = "",
    showTitle: Boolean = true,
) : QuickAbilityActivity(
    contentWithTitle(
        layoutId, layoutView, layoutViewInContext, titleName, showTitle = showTitle, null, null
    ).with(dealBind(false)), titleName = titleName
) {

    lateinit var titleView: QuickTitleView

    @CallSuper
    override fun initView(view: View) {
        super.initView(view)
        QuickBindWrap.bind(this)
        titleView = view.findViewById(R.id.titleView)
        initTitle(titleView)
        initContentLayout(view.findViewById(R.id.contentLayout))
    }

    open fun initTitle(titleView: QuickTitleView) {

    }

    open fun initContentLayout(contentLayout: RelativeLayout) {

    }


}