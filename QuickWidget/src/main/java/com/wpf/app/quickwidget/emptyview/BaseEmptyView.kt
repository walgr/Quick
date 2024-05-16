package com.wpf.app.quickwidget.emptyview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.wpf.app.quickutil.helper.matchMarginLayoutParams
import com.wpf.app.quickutil.run.RunOnContextWithSelf

abstract class BaseEmptyView @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    open val layoutId: Int = 0,
    open val layoutView: RunOnContextWithSelf<ViewGroup, View>? = null,
    override var curState: EmptyViewState = Loading
) : LinearLayout(mContext, attrs, defStyleAttr), EmptyViewStateI {

    init {
        init()
    }

    private var emptyView: View? = null
    protected fun init() {
        emptyView = layoutView?.run(context, this) ?: View.inflate(context, layoutId, null)
        addView(emptyView, matchMarginLayoutParams())
        initView(emptyView!!)
    }

    abstract fun initView(view: View)

    override fun setVisibility(visibility: Int) {
        emptyView?.let {
            it.visibility = visibility
        } ?: let {
            super.setVisibility(visibility)
        }
    }
}