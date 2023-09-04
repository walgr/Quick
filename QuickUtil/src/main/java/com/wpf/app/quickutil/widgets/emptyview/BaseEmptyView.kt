package com.wpf.app.quickutil.widgets.emptyview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.wpf.app.quickutil.bind.RunOnContextWithSelf

abstract class BaseEmptyView @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    open val layoutId: Int = 0,
    open val layoutView: RunOnContextWithSelf<ViewGroup, View>? = null,
    override var curState: EmptyViewState = Loading()
) : LinearLayout(mContext, attrs, defStyleAttr), EmptyViewStateI {

    init {
        init()
    }

    private fun init() {
        val view = layoutView?.run(context, this) ?: View.inflate(context, layoutId, null)
        addView(view)
        initView(view)
        changeState(curState)
    }

    abstract fun initView(view: View)
}